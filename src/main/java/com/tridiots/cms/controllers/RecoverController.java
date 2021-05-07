package com.tridiots.cms.controllers;

import com.tridiots.cms.message.Message;
import com.tridiots.cms.utils.modeldao.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RecoverController extends Controller{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputLogin = request.getParameter("login");
        Message message = UserUtils.findUser(inputLogin);
        if(!message.getFlag()) {
            Message utilMessage = UserUtils.recoverUser(inputLogin);

            if(utilMessage.getFlag()) {
                request.setAttribute("authMessage", utilMessage.getMessage());
                //request.getRequestDispatcher("index.jsp").forward(request,response);

                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", message.getMessage());
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
        }
        
        
        

    }
}
