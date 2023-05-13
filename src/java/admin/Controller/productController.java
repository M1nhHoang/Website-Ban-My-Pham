/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package admin.Controller;

import ProcessData.CategoryDAO;
import ProcessData.ProductDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author HP
 */
@WebServlet(name = "productController", urlPatterns = {"/admin/product"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
    maxFileSize = 1024 * 1024 * 10,      // 10 MB
    maxRequestSize = 1024 * 1024 * 100  // 100 MB
)
public class productController extends HttpServlet {
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        
        // instance
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void load_page(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("products", productDAO.getProducts(null).values());
        request.setAttribute("categorys", categoryDAO.getCategory().values());
        request.setAttribute("content", "/WEB-INF/jsp/admin/product.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/layout.jsp");
        dispatcher.forward(request, response);
    }

    protected void saveImage(String fileName, InputStream fileContent){
        // Lấy đường dẫn tuyệt đối đến thư mục gốc của ứng dụng
        String appPath = getServletContext().getRealPath("");

        // Tạo đường dẫn tới thư mục lưu trữ hình ảnh
        String saveDirectory = appPath + File.separator + "public" + File.separator + "img";
        File file = new File(saveDirectory.replace("//", "/") + File.separator + fileName);
        try (OutputStream outputStream = new FileOutputStream(file)) {
            int bytesRead;
            byte[] buffer = new byte[15360];
            while ((bytesRead = fileContent.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            // Close the InputStream
            fileContent.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(productController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(productController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        String products_action = request.getParameter("action");
        
        if (products_action != null){
            int product_id = Integer.parseInt(request.getParameter("id"));
            productDAO = new ProductDAO();
            if (products_action.equals("delete")){
                productDAO.deleteProduct(product_id);
            }
            else if (products_action.equals("edit")){
                request.setAttribute("product_Detail", productDAO.getProducts(null).get(product_id));
            }
        }
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
        String action = request.getParameter("action");
        productDAO = new ProductDAO();
        if (action.equals("add")){
            String tenSanPham = request.getParameter("productName");
            Double donGia = Double.parseDouble(request.getParameter("productPrice"));
            int soLuong = Integer.parseInt(request.getParameter("productQuantity"));
            String moTa = request.getParameter("productDescription");
            int maDanhMuc = Integer.parseInt(request.getParameter("category"));
            
            // load images
            Part filePart = request.getPart("productImage");
            String fileName = filePart.getSubmittedFileName();
            
            // save images
            saveImage(fileName, filePart.getInputStream());
            
            productDAO.addProduct(tenSanPham, donGia, soLuong, fileName, moTa, maDanhMuc);
            
            response.sendRedirect("/WebsiteBanMyPham/admin/product");
        }
        else if (action.equals("edit")){
            int maSanPham = Integer.parseInt(request.getParameter("productId"));
            String tenSanPham = request.getParameter("productName");
            Double donGia = Double.parseDouble(request.getParameter("productPrice"));
            int soLuong = Integer.parseInt(request.getParameter("productQuantity"));
            String moTa = request.getParameter("productDescription");
            int maDanhMuc = Integer.parseInt(request.getParameter("category"));
            String fileName = request.getParameter("last_img");
            
            // load images
            Part filePart = request.getPart("productImage");
            if (filePart.getSize() != 0){
                fileName = filePart.getSubmittedFileName();
                // save images
                saveImage(fileName, filePart.getInputStream());
            }
            
            productDAO.editProduct(maSanPham, tenSanPham, donGia, soLuong, fileName, moTa, maDanhMuc);
            
            response.sendRedirect("/WebsiteBanMyPham/admin/product");
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
