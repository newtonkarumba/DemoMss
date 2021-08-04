//package com.systech.mss.web;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerResponseContext;
//import javax.ws.rs.container.ContainerResponseFilter;
//import javax.ws.rs.ext.Provider;
//import java.io.IOException;
//
////@Provider
//@WebFilter(
//        asyncSupported = true,
//        urlPatterns = {"/*"})
//public class CORSFilter implements Filter {
//
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {}
//
//    @Override
//    public void doFilter(
//            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//            throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, HEAD, OPTIONS, DELETE");
//        response.setHeader(
//                "Access-Control-Allow-Headers",
//                "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//    @Override
//    public void destroy() {}
//}
//
//
//
//
//
//
