package com.tridiots.cms.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tridiots.cms.kanye.IO;
import com.tridiots.cms.models.User;
import java.io.IOException;
import java.util.ArrayList;

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
		
		int[] uCodes = new int[]{111,222,333,444};
		ArrayList<Integer> userRoleCodes = new ArrayList<>();  
		for(int c: uCodes) {userRoleCodes.add(c);}
		if(roleSessionChecker(httpRequest, userRoleCodes)) {
			IO.println("Session valid");
            filterChain.doFilter(httpRequest, httpResponse);
		} else {
			IO.println("Session invalid");
            httpRequest.getSession().setAttribute("errorMessage", "You have to be logged in first!");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
		}
        

    }
    
    public static boolean roleSessionChecker(HttpServletRequest httpRequest, ArrayList<Integer> roleCodes) {
    	HttpSession session = httpRequest.getSession();
    	if(session != null && session.getAttribute("loggedInUser") != null) {
    		User loggedInUser = (User) session.getAttribute("loggedInUser");
    		IO.println(loggedInUser.getUserRole());
    		return roleCodes.contains(loggedInUser.getUserRole()); 
    	}
    	return false;
    }

}
