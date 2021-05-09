package com.tridiots.cms.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tridiots.cms.models.User;
import com.tridiots.cms.shortcuts.IO;

import java.io.IOException;

public class AuthUserFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		
		int adminRoleCode = 111;
		if(roleSessionChecker(httpRequest, adminRoleCode)) {
			IO.println("Session valid");
            filterChain.doFilter(httpRequest, httpResponse);
		} else {
			IO.println("Session invalid");
            httpRequest.getSession().setAttribute("errorMessage", "You have to be logged in first!");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
		}
        

    }
    
    public class AuthAdminFilter implements Filter {

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
    		HttpServletResponse httpResponse = (HttpServletResponse) response;
    		
    		int adminRoleCode = 111;
    		if(roleSessionChecker(httpRequest, adminRoleCode)) {
    			IO.println("Admin session valid");
    			chain.doFilter(httpRequest, httpResponse); 
    		} else {
    			IO.println("Admin session invalid");
    			httpResponse.sendRedirect(httpRequest.getContextPath() + "/error/403.html");
    		}
			
		}
    	
    }
    
    public class AuthJudgeFilter implements Filter {
    	@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
    		HttpServletRequest httpRequest = (HttpServletRequest) request;
    		HttpServletResponse httpResponse = (HttpServletResponse) response;
    		
    		int judgeRoleCode = 222;
    		if(roleSessionChecker(httpRequest, judgeRoleCode)) {
    			IO.println("Judge session valid");
    			chain.doFilter(httpRequest, httpResponse); 
    		} else {
    			IO.println("Judge session invalid");
    			httpResponse.sendRedirect(httpRequest.getContextPath() + "/error/403.html");
    		}
		}
    }
    
    public class AuthContestantFilter implements Filter {
    	@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
    		HttpServletRequest httpRequest = (HttpServletRequest) request;
    		HttpServletResponse httpResponse = (HttpServletResponse) response;
    		
    		int contestantRoleCode = 333;
    		if(roleSessionChecker(httpRequest, contestantRoleCode)) {
    			IO.println("COntestant session valid");
    			chain.doFilter(httpRequest, httpResponse);
    		} else {
    			IO.println("Contestant session invalid");
    			httpResponse.sendRedirect(httpRequest.getContextPath() + "/error/403.html");
    		}
			
		}
    }
    
    public boolean roleSessionChecker(HttpServletRequest httpRequest, int roleCode) {
    	HttpSession session = httpRequest.getSession();
    	
    	if(session != null) {
    		User loggedInUser = (User) session.getAttribute("loggedInUser");
    		return (loggedInUser != null && loggedInUser.getUserRole() == roleCode);
    	}
    	return false;
    }

}
