package com.xws.bootpro.utils.Interceptor;

import com.xws.bootpro.utils.RequestUtil.RequestBodyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *@Author：lm
 *@Date：2022/8/10 10:03
 *@Description 全局拦截器
*/

@Configuration
public class MyApiConfig extends WebMvcConfigurerAdapter {

    /**
     * 注册自定义的拦截器 SignatureInterceptor
     *
     * @return
     */
    @Bean
    public LoginInterceptor LoginInterceptor() {
        return new LoginInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        // 注册API拦截器
        //配置多个不拦截地址：   .excludePathPatterns("/login/userlogin","/xx/xx/xxx")
        registry.addInterceptor(LoginInterceptor()).addPathPatterns("/api/**").excludePathPatterns("/login/userlogin");
    }

    /**
     * 对指定请求的 HttpServletRequest 进行重新注册返回
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean setLogServiceFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        RequestBodyFilter requestBodyFilter = new RequestBodyFilter();
        registrationBean.setFilter(requestBodyFilter);
        registrationBean.setName("interceptor filter body params");
        registrationBean.addUrlPatterns("/api/**");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}


