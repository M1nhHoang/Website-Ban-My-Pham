/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package user.Controller;

import Entity.User;
import ProcessData.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LE QUANG BAO CUONG
 */
@WebServlet(name = "loginController", urlPatterns = {"/login"})
public class loginController extends HttpServlet {
    private UserDAO userDAO;
    private HttpSession session;
    
    @Override
    public void init() throws ServletException {
        super.init();
        
        // instance
        userDAO = new UserDAO();
    }
        
    protected void loginRequest (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //instance
        userDAO = new UserDAO();
        
        // get HttpSession object
        session = request.getSession();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        User user = userDAO.login(username);
        if (user != null && user.getName().equals(username) && user.getPassword().equals(password)){
            // set attribute for admin information
            session.setAttribute("user", user); 
            session.setMaxInactiveInterval(60*60);
            
            // redirct if login success
            response.sendRedirect("/WebsiteBanMyPham/");
        }
        else {
            // wrong account
            request.setAttribute("loginError", "Tài khoản không tồn tại!");
            loadPage(request, response);
        }
    }
 
    protected void loadPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("content","/WEB-INF/jsp/user/login.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher ("/WEB-INF/jsp/layout.jsp");
        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        loadPage(request,response);
    }
    
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        loginRequest(request,response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>>

}
