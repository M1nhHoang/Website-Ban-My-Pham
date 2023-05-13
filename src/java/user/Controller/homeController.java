/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package user.Controller;

import Entity.User;
import ProcessData.CartDAO;
import ProcessData.CategoryDAO;
import ProcessData.OrderDAO;
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
@WebServlet(name = "homeController", urlPatterns = {"/home"})
public class homeController extends HttpServlet {
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private CartDAO cartDAO;
    private OrderDAO orderDAO;
    private HttpSession session;
    
    @Override
    public void init() throws ServletException {
        super.init();
        
        // instance
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        cartDAO = new CartDAO();
        orderDAO = new OrderDAO();
    }
    
    protected void load_page(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get HttpSession object
        session = request.getSession();
        
        // get request
        String danhMuc = request.getParameter("danhmuc");
        
        // create cart if user has login
        if (session.getAttribute("user") != null){
            int userId = ((User)session.getAttribute("user")).getUser_id();
            cartDAO.create_cart(userId);
            
            request.setAttribute("cartItemsCounter", orderDAO.getCartDetails(userId).size());
        }
        
        // setAttribute
        request.setAttribute("products", productDAO.getProducts(danhMuc).values());
        request.setAttribute("categorys", categoryDAO.getCategory().values());
        request.setAttribute("product", "/WEB-INF/jsp/user/product.jsp");
        request.setAttribute("category", "/WEB-INF/jsp/user/category.jsp");
        request.setAttribute("content", "/WEB-INF/jsp/user/home.jsp");
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
