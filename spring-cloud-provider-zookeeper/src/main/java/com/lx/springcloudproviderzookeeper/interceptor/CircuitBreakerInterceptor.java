package com.lx.springcloudproviderzookeeper.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lanxing
 */
public class CircuitBreakerInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, @Nullable Exception ex) throws IOException {
        if("/way3".equals(request.getRequestURI()) && ex instanceof TimeoutException){
            response.getWriter().write(errorContent());
        }
    }

    public String errorContent() {
        return "fault";
    }
}
