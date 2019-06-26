package com.accenture.flowershop.fe.servlets.filter;

import com.accenture.flowershop.fe.dto.user.UserDTO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/cart", "/orders/*", "/myorders/*"})
public class CheckUserSessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();

        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/");
        }
        else
            filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {
    }
}
