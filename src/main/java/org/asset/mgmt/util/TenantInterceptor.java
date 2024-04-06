package org.asset.mgmt.util;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;
import java.util.Objects;

@Component
public class TenantInterceptor implements Filter {

    @Value("${defaultTenant}")
    private String defaultTenant;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        String tenantID = request.getHeader("X-TenantID");
      MultiTenancyContext.setTenant(Objects.requireNonNullElse(tenantID, defaultTenant));
        try {
            filterChain.doFilter(request, servletResponse);
        } finally {
            MultiTenancyContext.clear();
        }
    }
    //
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String tenantID = request.getHeader("X-TenantID");
//        MultiTenancyContext.setTenant(Objects.requireNonNullElse(tenantID, "public"));
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        MultiTenancyContext.clear();
//    }
}
