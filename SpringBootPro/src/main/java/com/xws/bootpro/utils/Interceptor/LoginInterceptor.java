package com.xws.bootpro.utils.Interceptor;

import com.xws.bootpro.dataobj.Enum.StatusCodeEnum;
import com.xws.bootpro.dataobj.PaUserActionDo;
import com.xws.bootpro.dataobj.PaUserDo;
import com.xws.bootpro.dataobj.PaUserSessionDo;
import com.xws.bootpro.mapper.PaUserActionMapper;
import com.xws.bootpro.mapper.PaUserMapper;
import com.xws.bootpro.mapper.PaUserSessionMapper;
import com.xws.bootpro.utils.CommonUtil;
import com.xws.bootpro.utils.CustomException;
import com.xws.bootpro.utils.DateUtil;
import com.xws.bootpro.utils.RequestUtil.RequestReaderHttpServletRequestWrapper;
import com.xws.bootpro.utils.sessionUtil.MySessionContext;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 *@Author：lm
 *@Date：2022/8/10 9:49
 *@Description 自定义拦截器拦截方法
*/

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    PaUserSessionMapper paUserSessionMapper;

    @Resource
    PaUserMapper paUserMapper;

    @Resource
    PaUserActionMapper paUserActionMapper;

    /**
     * 程序执行之前调用
     *
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        //获取用户code
        String userCode = "";
        try {
            // 创建重写后的 HttpServletRequest
            RequestReaderHttpServletRequestWrapper wrapper = new RequestReaderHttpServletRequestWrapper(request);
            // 获取 body 参数
            String result = wrapper.inputStream2String(wrapper.getInputStream());
            JSONObject jsonObject = JSONObject.fromObject(result);
            userCode = jsonObject.getString("userCode");
        }catch (Exception e){
            log.info(StatusCodeEnum.SC101.getMsg() + "：" + e.getMessage());
            throw new CustomException(StatusCodeEnum.SC101.getCode(),StatusCodeEnum.SC101.getMsg());
        }

        //判断是否存在这个用户
        PaUserDo paUserDo = new PaUserDo();
        paUserDo.setUsercode(userCode);
        paUserDo.setState(0);
        PaUserDo newuser = paUserMapper.selectOne(paUserDo);
        if(null == newuser){
            log.info(StatusCodeEnum.SC101.getMsg());
            throw new CustomException(StatusCodeEnum.SC101.getCode(),StatusCodeEnum.SC101.getMsg());
        }

        //通过数据库获取sessionid
        PaUserSessionDo sessiondo = paUserSessionMapper.selectByPrimaryKey(userCode);
        String sessionid = sessiondo.getSessionid();
        if(StringUtils.isBlank(sessionid) || StringUtils.isBlank(userCode)){
            log.info(StatusCodeEnum.SC101.getMsg());
            throw new CustomException(StatusCodeEnum.SC101.getCode(),StatusCodeEnum.SC101.getMsg());
        }

        //判断cookie中的sessionid是否和数据库中sessionid相同
        Cookie[] cookies = request.getCookies();//获取Cookie信息
        String cookieValue = CommonUtil.getCookieValue(cookies,userCode);
        if(StringUtils.isBlank(cookieValue) || !cookieValue.equals(sessionid)){
            log.info(StatusCodeEnum.SC101.getMsg());
            throw new CustomException(StatusCodeEnum.SC101.getCode(),StatusCodeEnum.SC101.getMsg());
        }

        // 通过sessionid获取当前session
        MySessionContext myc= MySessionContext.getInstance();
        HttpSession session = myc.getSession(sessionid);
        if(null == session){
            log.info(StatusCodeEnum.SC101.getMsg());
            throw new CustomException(StatusCodeEnum.SC101.getCode(),StatusCodeEnum.SC101.getMsg());
        }
        // 根据session获取登录用户
        PaUserDo ui = (PaUserDo) session.getAttribute("paUserDo");
        // 没登录，重定向到登录页面
        if (null == ui) {
            log.info(StatusCodeEnum.SC101.getMsg());
            throw new CustomException(StatusCodeEnum.SC101.getCode(),StatusCodeEnum.SC101.getMsg());
        }

        //操作记录
        String requestUrl = request.getRequestURI();
        PaUserActionDo paUserActionDo = new PaUserActionDo();
        paUserActionDo.setUsercode(userCode);
        paUserActionDo.setRequesturl(requestUrl);
        paUserActionDo.setActiontime(DateUtil.dateToTime(new Date(),"yyyy-MM-dd HH:mm:ss"));
        paUserActionMapper.insert(paUserActionDo);

        // 已经登录
        return true;
    }

    /**
     * 程序执行之后执行
     *
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 完成请求处理后（即渲染视图之后）的回调
     *
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
    }

}

