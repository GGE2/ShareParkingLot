package com.example.coreweb.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//필터를 특정 url 에만 적용하고싶으면
//@WebFilter에 원하는 url 지정
//애플리케이션 메인메서드에 @ServletComponentScan 붙힌다.
@WebFilter(urlPatterns = "/*")
@Slf4j
public class GlobalFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //필터에서는 request , response 객체를 변경할 수 있다.

        //전 처리
        ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest)request);
        ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse)response);


        chain.doFilter(httpServletRequest,httpServletResponse);

        //후 처리


        //req
        String requestURI = httpServletRequest.getRequestURI();
        String reqContent = new String(httpServletRequest.getContentAsByteArray());
        log.info("request url : {}  ,request body:{}",requestURI,reqContent);

        //resp
        int httpStatus = httpServletResponse.getStatus();
        String respContent = new String(httpServletResponse.getContentAsByteArray());
        log.info("response status:{} , responseBody:{}",httpStatus,respContent);
        httpServletResponse.copyBodyToResponse();
    }

    @Override
    public void destroy() {
    }
}