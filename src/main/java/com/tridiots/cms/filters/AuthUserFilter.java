package com.tridiots.cms.filters;

import com.tridiots.cms.models.User;
import com.tridiots.cms.shortcuts.IO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthUserFilter extends AuthFilter{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        HttpSession session = httpRequest.getSession();
        if(session != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            if(loggedInUser != null) {
                IO.println("Session valid");
                filterChain.doFilter(httpRequest, httpResponse);
                //httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/profile.jsp");
            } else {
                IO.println("Session invalid");
                httpRequest.getSession().setAttribute("errorMessage", "You have to be logged in first!");
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            }
        }
    }
}
