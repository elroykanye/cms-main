package com.tridiots.cms.filters.authuser;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tridiots.cms.filters.AuthUserFilter;
import com.tridiots.cms.kanye.IO;

/**
 * Servlet Filter implementation class JudgeFilter
 */
public class JudgeFilter extends AuthUserFilter {

    /**
     * Default constructor. 
     */
    public JudgeFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		ArrayList<Integer> judgeRoleCode = new ArrayList<>();
		judgeRoleCode.add(222);
		if(AuthUserFilter.roleSessionChecker(httpRequest, judgeRoleCode)) {
			IO.println("Judge session valid");
			chain.doFilter(httpRequest, httpResponse); 
		} else {
			IO.println("Judge session invalid");
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/error/403.html");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
