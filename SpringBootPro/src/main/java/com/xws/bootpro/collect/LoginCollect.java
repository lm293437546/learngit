package com.xws.bootpro.collect;

import com.xws.bootpro.dataobj.Enum.StatusCodeEnum;
import com.xws.bootpro.dataobj.PaUserDo;
import com.xws.bootpro.dataobj.PaUserLogDo;
import com.xws.bootpro.dataobj.PaUserSessionDo;
import com.xws.bootpro.dataobj.PaWaresActionDo;
import com.xws.bootpro.dataobj.request.BrowseWaresRequestDto;
import com.xws.bootpro.dataobj.request.LoginRequestDto;
import com.xws.bootpro.mapper.*;
import com.xws.bootpro.utils.CommonUtil;
import com.xws.bootpro.utils.CreateHttp.SysContent;
import com.xws.bootpro.utils.CustomException;
import com.xws.bootpro.utils.DateUtil;
import com.xws.bootpro.utils.RSA.RSAUtil;
import com.xws.bootpro.utils.ipUtil.ReginIpUtil;
import com.xws.bootpro.utils.oshi.OshiUtils;
import com.xws.bootpro.utils.sessionUtil.ManageSession;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 *@Author：lm
 *@Date：2022/8/9 17:07
 *@Description 用户登录操作类
*/

@RestController
@RequestMapping("login")
//使用Slf4j日志门面
@Slf4j
public class LoginCollect {

    @Resource
    PaUserMapper paUserMapper;

    @Resource
    PaUserSessionMapper paUserSessionMapper;

    @Resource
    PaUserLogMapper paUserLogMapper;

    @Resource
    PaWaresActionMapper paWaresActionMapper;

    @Autowired
    private Ip2regionSearcher ip2regionSearcher;

    @Resource
    PaCfgSystemMapper paCfgSystemMapper;

    //全局变量
    @Autowired
    private ManageSession manageSession;

    /**
     * 验证用户
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("userlogin")
    public String userlogin(@RequestBody LoginRequestDto request) throws Exception{
        if(StringUtils.isBlank(request.getUserCode()) || StringUtils.isBlank(request.getPassword())){
            log.info(StatusCodeEnum.SC100.getMsg());
            throw new CustomException(StatusCodeEnum.SC100.getCode(),StatusCodeEnum.SC100.getMsg());
        }
        //获取私钥
        String privateKey = paCfgSystemMapper.selectByPrimaryKey(StatusCodeEnum.RSA_PRIVATEKEY.getCode()).getFldval();
        //用户输错密码次数将锁定
        String user_lock_number = paCfgSystemMapper.selectByPrimaryKey(StatusCodeEnum.USER_LOCK_NUMBER.getCode()).getFldval();
        //自动解锁时间：分钟
        String user_unlock_date = paCfgSystemMapper.selectByPrimaryKey(StatusCodeEnum.USER_UNLOCK_DATE.getCode()).getFldval();
        boolean flag = false;
        boolean lockFlag = false;   //未解锁
        PaUserDo paUserDo = new PaUserDo();
        paUserDo.setUsercode(request.getUserCode());
        PaUserDo newuser = paUserMapper.selectOne(paUserDo);
        if(null == newuser){
            throw new CustomException(StatusCodeEnum.SC100.getCode(),StatusCodeEnum.SC100.getMsg());
        }else{
            if(newuser.getState() == 2){
                //用户锁定
                if(null == newuser.getLastloginerrortime()){
                    lockFlag = true;
                }else{
                    long errorTime = newuser.getLastloginerrortime().getTime();
                    long nowTime = System.currentTimeMillis();
                    long time = (nowTime - errorTime) / (1000 * 60);
                    if (Integer.parseInt(user_unlock_date) - time <= 0) {
                        lockFlag = true;
                    }
                }
            }else if(newuser.getState() == 1){
                //用户失效
                throw new CustomException(StatusCodeEnum.SC100.getCode(),StatusCodeEnum.SC100.getMsg());
            }else{
                lockFlag = true;
            }
        }
        if(lockFlag){
            //已解锁
            String decrypt = RSAUtil.decrypt(newuser.getPassword(), privateKey);
            if(!decrypt.equals(request.getUserCode() + request.getPassword())){
                flag = true;
            }
        }else{
            throw new CustomException(StatusCodeEnum.SC107.getCode(),StatusCodeEnum.SC107.getMsg());
        }
        PaUserDo udo = new PaUserDo();
        udo.setUsercode(newuser.getUsercode());
        udo.setLastloginerrortime(DateUtil.dateToTime(new Date(),"yyyy-MM-dd HH:mm:ss"));
        if (flag){
            //登录失败
            Cookie ck = new Cookie(request.getUserCode(), null);
            ck.setMaxAge(-1);
            ck.setPath("/");
            SysContent.getResponse().addCookie(ck);

            if(null == newuser.getLoginerrorcount() || null == newuser.getLastloginerrortime()){
                udo.setLoginerrorcount(1);
            }else{
                if((newuser.getLoginerrorcount() + 1) >= Integer.parseInt(user_lock_number)){
                    udo.setState(2);
                }
                udo.setLoginerrorcount(newuser.getLoginerrorcount() + 1);
//                long timeError = newuser.getLastloginerrortime().getTime();
//                long timeNow = System.currentTimeMillis();
//                long time = (timeNow - timeError) / (1000 * 60);
//                //如果两次的登陆失败的时间差大于5分钟，则不计错误次数
//                if (time < 5) {
//                    if((newuser.getLoginerrorcount() + 1) >= Integer.parseInt(user_lock_number)){
//                        udo.setState(2);
//                    }
//                    udo.setLoginerrorcount(newuser.getLoginerrorcount() + 1);
//                }
            }

            //更新状态
            paUserMapper.updateByPrimaryKeySelective(udo);

            log.info(StatusCodeEnum.SC100.getMsg());
            throw new CustomException(StatusCodeEnum.SC100.getCode(),StatusCodeEnum.SC100.getMsg());
        }else{
            //登录成功
            udo.setLoginerrorcount(0);
            udo.setState(0);
            //更新状态
            paUserMapper.updateByPrimaryKeySelective(udo);
        }

        //获取session
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest().getSession();
        // 在sesssion 中存储用户信息
        session.setAttribute("paUserDo", newuser);
        //设置session过期时间,默认是1800s，单位为s
        String maxInterval = paCfgSystemMapper.selectByPrimaryKey(StatusCodeEnum.SESSION_MAX_INTERVAL.getCode()).getFldval();
        session.setMaxInactiveInterval(Integer.parseInt(maxInterval) * 60);

        //修改用户sessionid值
        String sessionid = session.getId();
        PaUserSessionDo paUserSessionDo = new PaUserSessionDo();
        paUserSessionDo.setUsercode(request.getUserCode());
        paUserSessionDo.setSessionid(sessionid);
        paUserSessionMapper.updateByPrimaryKeySelective(paUserSessionDo);

        Cookie ck = new Cookie(request.getUserCode(), sessionid);
        ck.setMaxAge(-1);
        ck.setPath("/");
        SysContent.getResponse().addCookie(ck);

        //每个账号只能在线一个,当前登录后其他的踢掉
//        HttpSession httpSession = manageSession.getManageSession().get(request.getUserCode());
//        if (httpSession!=null){
//            //session销毁
//            httpSession.invalidate();
//        }
//        //写入session信息
//        manageSession.getManageSession().put(request.getUserCode(),session);

        //登录日志
        PaUserLogDo paUserLogDo = new PaUserLogDo();
        paUserLogDo.setUsercode(newuser.getUsercode());
        paUserLogDo.setUsername(newuser.getUsername());
        String ip = ReginIpUtil.getIpAddress();
        paUserLogDo.setIp(ip);
        paUserLogDo.setAddr(ip2regionSearcher.getAddress(ip));
        paUserLogDo.setLogtime(DateUtil.dateToTime(new Date(),"yyyy-MM-dd HH:mm:ss"));
        paUserLogMapper.insert(paUserLogDo);

        log.info(request.getUserCode() + "登录系统成功，登录时间为：" + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));

        return newuser.getUsercode();
    }

    /**
     * 记录浏览商品记录
     */
    @RequestMapping("browseWares")
    public void browseWares(@RequestBody BrowseWaresRequestDto request) throws Exception{
        PaWaresActionDo paWaresActionDo = new PaWaresActionDo();
        String ip = ReginIpUtil.getIpAddress();
        paWaresActionDo.setUserip(ip);
        paWaresActionDo.setUseraddr(ip2regionSearcher.getAddress(ip));
        paWaresActionDo.setWarescode(request.getWarescode());
        paWaresActionDo.setActiontime(DateUtil.dateToTime(new Date(),"yyyy-MM-dd HH:mm:ss"));
        paWaresActionMapper.insert(paWaresActionDo);
    }

    /**
     * 记录浏览者信息
     */
    @RequestMapping("getViewersInfo")
    public String getViewersInfo() throws Exception{
        String info = "";
        log.info("------------------------------------------------------------");
        //设备
        boolean sbflag = false;
        String userAgent = SysContent.getRequest().getHeader("user-agent").toLowerCase();
        if(userAgent.indexOf("android") != -1){
            log.info("设备：安卓");
            info = info + "\n" + "设备：安卓";
        }else if(userAgent.indexOf("iphone") != -1){
            log.info("设备：iphone");
            info = info + "\n" + "设备：iphone";
        }else if(userAgent.indexOf("ipad") != -1){
            log.info("设备：ipad");
            info = info + "\n" + "设备：ipad";
        }else if(userAgent.indexOf("ipod") != -1){
            log.info("设备：ipod");
            info = info + "\n" + "设备：ipod";
        }else{
            log.info("设备：PC");
            info = info + "\n" + "设备：PC";
            sbflag = true;
        }
        //IP
        String ip = ReginIpUtil.getIpAddress();
        log.info("IP:" + ip);
        info = info + "\n" + "IP:" + ip;
        log.info("IP地址：" + ReginIpUtil.getDetailIpAddr(ip));
        info = info + "\n" + "IP地址：" + ReginIpUtil.getDetailIpAddr(ip);

        if (sbflag){
            log.info("PC品牌：" + OshiUtils.getPcBrand());
            info = info + "\n" + "PC品牌：" + OshiUtils.getPcBrand();
            log.info("系统版本号：" + OshiUtils.getPcSysVersion());
            info = info + "\n" + "系统版本号：" + OshiUtils.getPcSysVersion();
            log.info("处理器型号：" + OshiUtils.getPcProcessor());
            info = info + "\n" + "处理器型号：" + OshiUtils.getPcProcessor();
            log.info("序列号（S/N码）：" + OshiUtils.getPcSN());
            info = info + "\n" + "序列号（S/N码）：" + OshiUtils.getPcSN();
            log.info("处理器ID(CPUID)：" + OshiUtils.getPcCPUID());
            info = info + "\n" + "处理器ID(CPUID)：" + OshiUtils.getPcCPUID();
            log.info("通用唯一识别码(UUID)：" + OshiUtils.getPcUUID());
            info = info + "\n" + "通用唯一识别码(UUID)：" + OshiUtils.getPcUUID();
            log.info("内存条信息：" + OshiUtils.getPcMemory());
            info = info + "\n" + "内存条信息：" + OshiUtils.getPcMemory();
            log.info("磁盘信息：" + OshiUtils.getPcFile());
            info = info + "\n" + "磁盘信息：" + OshiUtils.getPcFile();
        }
        log.info("------------------------------------------------------------");
        return info;
    }

}
