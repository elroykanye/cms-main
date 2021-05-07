package com.tridiots.cms.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public abstract class Controller extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getMethod().toLowerCase(Locale.ROOT).equals("post")) {
            doPost(request, response);
        }
        if(request.getMethod().toLowerCase(Locale.ROOT).equals("get")) {
            doGet(request, response);
        }
    }

    protected abstract void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException ;
    protected abstract void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException ;

}
