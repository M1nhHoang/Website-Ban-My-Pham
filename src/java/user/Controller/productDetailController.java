/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package user.Controller;

import ProcessData.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HP
 */
@WebServlet(name = "productDetailController", urlPatterns = {"/detail"})
public class productDetailController extends HttpServlet {
    private ProductDAO productDAO;
    private HttpSession session;
    
    @Override
    public void init() throws ServletException {
        super.init();
        
        // instance
        productDAO = new ProductDAO();
    }
    protected void load_page(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get HttpSession object
        session = request.getSession();
        
        // get request
        String masanpham = request.getParameter("sanpham");
        
        // create cart if user has login
        if (session.getAttribute("user") != null){
//            request.setAttribute("cart-items", );
        }
        
        // setAttribute
        if (masanpham != null){
            request.setAttribute("products", productDAO.getProducts(null).get(Integer.parseInt(masanpham)));
        }
        
        request.setAttribute("content", "/WEB-INF/jsp/user/detail.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/layout.jsp");
        dispatcher.forward(request, response);
    }
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        load_page(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
