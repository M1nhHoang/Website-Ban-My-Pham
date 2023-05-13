/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package user.Controller;

import Entity.User;
import ProcessData.OrderDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author HP
 */
@WebServlet(name = "cartDetailController", urlPatterns = {"/cartDetail"})
public class cartDetailController extends HttpServlet {
    private OrderDAO orderDAO;
    private HttpSession session;
    
    @Override
    public void init() throws ServletException {
        super.init();
        
        // instance
        orderDAO = new OrderDAO();
    }
    
    protected void load_page(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        // get HttpSession object
        session = request.getSession();
        
        request.setAttribute("cartItemsCounter", orderDAO.getCartDetails(((User) session.getAttribute("user")).getUser_id()).size());
        request.setAttribute("orders",orderDAO.getCartDetails(((User) session.getAttribute("user")).getUser_id()).values());
        request.setAttribute("content", "/WEB-INF/jsp/user/cartDetail.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/layout.jsp");
        dispatcher.forward(request, response);
    }
    
    protected void order(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        // Đọc dữ liệu từ request
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String jsonInput = reader.readLine();
        
        // Chuyển đổi dữ liệu từ định dạng JSON sang đối tượng JSONArray
        JSONArray jsonArray = new JSONArray(jsonInput);

        // Lặp qua từng sản phẩm trong JSONArray
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int productId = jsonObject.getInt("product");
            int quantity = jsonObject.getInt("quantity");
            int user_id = ((User) session.getAttribute("user")).getUser_id();//(int) session.getAttribute("user_id");
            
            orderDAO.Order(user_id, productId, quantity);
        }
    }
    
    protected void remove(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        // Đọc dữ liệu từ request
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String jsonInput = reader.readLine();
        
        // Chuyển đổi dữ liệu từ định dạng JSON sang đối tượng JSONArray
        JSONArray jsonArray = new JSONArray(jsonInput);

        // Lặp qua từng sản phẩm trong JSONArray
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int productId = jsonObject.getInt("product");
            int user_id = ((User) session.getAttribute("user")).getUser_id();//(int) session.getAttribute("user_id");
            
            orderDAO.RemoveCartDetails(user_id, productId);
        }
    }
    
    protected void add(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        // Đọc dữ liệu từ request
        int productId = Integer.parseInt(request.getParameter("masanpham"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        
        orderDAO.AddCartDetails(((User) session.getAttribute("user")).getUser_id(), productId, quantity);
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
        // get HttpSession object
        session = request.getSession();
        
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
        // get HttpSession object
        session = request.getSession();
        
        // Đọc dữ liệu từ request
        String action = request.getParameter("action");
        if (action.equals("order")){
            order(request, response);
        }
        else if (action.equals("remove")){
            remove(request, response);
        }
        else if (action.equals("add")){
            add(request, response);
        }
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
