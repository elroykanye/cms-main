package com.tridiots.cms.controllers.judge;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tridiots.cms.controllers.UsersController;
import com.tridiots.cms.models.User;
import com.tridiots.cms.utils.modeldao.UserUtils;

/**
 * Servlet implementation class ConsController
 */
@WebServlet("/ConsController")
public class ConsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        Integer gottenConId = null;
        if(request.getParameter("conid") != null) gottenConId = Integer.parseInt(request.getParameter("conid"));
        switch (request.getParameter("action").toLowerCase()) {
            case "edit":
                doEditUser(request, response, (int)gottenConId);
                break;
            case "delete":
                try {
                    doDeleteUser(request, response, (int) gottenConId);
                } catch (SQLException ex) {
                    Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case "delete all":
                doDeleteAll(request, response);
                break;
            default:
                break;
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	private void doEditUser(HttpServletRequest request, HttpServletResponse response, int uid) throws ServletException, IOException {
        User user = UserUtils.getUser(uid);
        request.getSession().setAttribute("currentUser", user);
        response.sendRedirect("user/admin/user.jsp");
    }
    
    private void doDeleteUser(HttpServletRequest request, HttpServletResponse response, int uid) throws ServletException, IOException, SQLException {
        User user = new User();
        user.setUserId(uid);
        UserUtils.deleteUser(user);
        response.sendRedirect("user/admin/users.jsp");
    }
    private void doDeleteAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserUtils.deleteAllUsers();
        response.sendRedirect("user/admin/users.jsp");
    }

}
