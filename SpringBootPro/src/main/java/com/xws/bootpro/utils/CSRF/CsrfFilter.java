package com.xws.bootpro.utils.CSRF;

import com.xws.bootpro.dataobj.Enum.StatusCodeEnum;
import com.xws.bootpro.utils.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *@Author：lm
 *@Date：2022/11/25 9:53
 *@Description 防止CSRF攻击
*/
@WebFilter(urlPatterns = "/*",filterName = "csrfFilter")
@Slf4j
public class CsrfFilter implements Filter {

    //本系统的访问路径
    @Value("${csrf.baseurl}")
    private String baseurL;

    //是否启用过滤器（N：未开启，Y：开启）
    @Value("${csrf.enabledbasefilter}")
    private String enabledBaseFilter;

    //无需拦截的URL集合（英文逗号隔开）
    @Value("${csrf.openurllist}")
    private String openUrls;

    public void doFilter (ServletRequest request,ServletResponse response,FilterChain chain)throws IOException, ServletException {
        if (enabledBaseFilter == null || baseurL == null || enabledBaseFilter.equals("N")){
            chain.doFilter (request, response);
            return;
        }
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String conString = req.getHeader("REFERER");  //获取父ur1--如果不是直接输入的话就是先前的访问过来的页面，要是用户输入了，这个url是不存在的
        if("".equals(conString) || null == conString){    //判断如果上一个目录为空的话。说明是用户直接输入url访问的
            String servletPath = req.getServletPath();   //当前请求url，去掉几个可以直接访问的页面
            if(StringUtils.isNotBlank(openUrls)){
                String[] openUrlList = openUrls.split(",");
                for (String url : openUrlList){
                    if (servletPath.equals(url)){
                        chain.doFilter(request,response);
                        return;
                    }
                }
            }
            //resp.sendRedirect(baseurL);
            log.info("CSRF启动，无效访问!");
            throw new CustomException(StatusCodeEnum.SC106.getCode(),StatusCodeEnum.SC106.getMsg());
        }else{
            //判断请求是否来源于本系统
            if(conString.trim().startsWith(baseurL)){
                chain.doFilter(request,response);
            }else {
                //resp.sendRedirect(baseurL);
                log.info("CSRF启动，无效访问!");
                throw new CustomException(StatusCodeEnum.SC106.getCode(),StatusCodeEnum.SC106.getMsg());
            }
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException{

    }

    public void destroy(){

    }

}