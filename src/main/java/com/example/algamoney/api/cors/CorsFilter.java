package com.example.algamoney.api.cors;

import com.example.algamoney.api.config.property.AlgamoneyApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Autowired
    private AlgamoneyApiProperty algamoneyApiProperty;

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse resp = (HttpServletResponse) response;

        resp.setHeader("Access-Control-Allow-Origin", algamoneyApiProperty.getOriginPermitida());
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        if ("OPTIONS".equals(request.getMethod()) && algamoneyApiProperty.getOriginPermitida().equals(request.getHeader("Origin"))) {
            resp.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
            resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
            resp.setHeader("Access-Control-Max-Age", "3600");


            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, response);
        }
    }
}
