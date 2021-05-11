package com.tridiots.cms.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tridiots.cms.kanye.IO;
import com.tridiots.cms.models.User;

import java.io.IOException;

public class AuthUserFilter implements Filter {
	public AuthUserFilter() {}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	@Override
	public void destroy() {
		
	}


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		
		int userRoleCode = 444;
		if(roleSessionChecker(httpRequest, userRoleCode)) {
			IO.println("Session valid");
            filterChain.doFilter(httpRequest, httpResponse);
		} else {
			IO.println("Session invalid");
            httpRequest.getSession().setAttribute("errorMessage", "You have to be logged in first!");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
		}
        

    }
    
    public static boolean roleSessionChecker(HttpServletRequest httpRequest, int roleCode) {
    	HttpSession session = httpRequest.getSession();
    	
    	if(session != null) {
    		User loggedInUser = (User) session.getAttribute("loggedInUser");
    		return (loggedInUser != null && loggedInUser.getUserRole() == roleCode);
    	}
    	return false;
    }

}
