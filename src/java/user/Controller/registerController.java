/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package user.Controller;

import ProcessData.UserDAO;
import java.io.IOException;
import Entity.User;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "registerController", urlPatterns = {"/register"})
public class registerController extends HttpServlet {
    private UserDAO userDAO;
    private HttpSession session;
    
    @Override
    public void init() throws ServletException {
        super.init();
        
        // instance
        userDAO = new UserDAO();
    }
    
    protected void registerRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get request parameter
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        String repass = request.getParameter("repass");
        
        // regis
        if (pass != null && (pass.equals(repass))) {
            User user = userDAO.checkExistAccount(name);
            if (user != null) {
                request.setAttribute("registrationError", "Tài khoản đã tồn tại!");
                loadPage(request, response);
            } else {
                // register
                userDAO.register(name, pass);
                
                response.sendRedirect("/WebsiteBanMyPham/login");
            }
        } else {
            request.setAttribute("registrationError", "Mật khẩu không khớp!");
            loadPage(request, response);
        }
    }

    protected void loadPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("content", "/WEB-INF/jsp/user/register.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/layout.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        loadPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        registerRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
