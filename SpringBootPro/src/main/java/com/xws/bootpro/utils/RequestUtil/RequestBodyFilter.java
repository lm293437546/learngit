package com.xws.bootpro.utils.RequestUtil;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *@Author：lm
 *@Date：2022/8/10 16:28
 *@Description 重新组装 HttpServletRequest 返回, 解决拦截器中从流中获取完 post 请求中的 body 参数，controller 层无法再次获取的问题
*/

@WebFilter
public class RequestBodyFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String method = httpServletRequest.getMethod();
            String contentType = httpServletRequest.getContentType() == null ? "" : httpServletRequest.getContentType();
            // 如果是POST请求并且不是文件上传
            if (HttpMethod.POST.name().equals(method) && !contentType.equals(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                // 重新生成ServletRequest  这个新的 ServletRequest 获取流时会将流的数据重写进流里面
                requestWrapper = new RequestReaderHttpServletRequestWrapper((HttpServletRequest) request);
            }
        }
        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
