/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProcessData;

import DataContext.SQLSERVERDataAccess;
import Entity.Cart;
import Entity.Category;
import Entity.Order;
import Entity.Product;
import Entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class OrderDAO {

    public OrderDAO() {
    }
    
    public Hashtable<Integer, Order> getCartDetails (int userId){
        SQLSERVERDataAccess d = null;
        try{
            d = new SQLSERVERDataAccess();  
        }
        catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        ResultSet rs = d.getResultSet_StoredProcedures("psgetCartDetails", new Object[]{userId});
        Hashtable<Integer, Order> list = new Hashtable<Integer, Order>();
        try {
            while(rs.next()){
                int maSanPham = rs.getInt(1);
                String tenSanPham = rs.getString(2);
                String moTa = rs.getString(3);
                String hinhAnh = rs.getString(4);
                Double donGia = rs.getDouble(5);
                int soLuong = rs.getInt(6);
                int Order_id = rs.getInt(8);
                int cart_id = rs.getInt(9);
                int quantity = rs.getInt(11);
                Date createAt = rs.getDate(12);
                int user_id = rs.getInt(13);
                String username = rs.getString(14);
                String password = rs.getString(15);
                String type = rs.getString(16);
                int maDanhMuc = rs.getInt(17);
                String tenDanhMuc = rs.getString(18);
                String danhMucHinhAnh = rs.getString(19);
                String DanhMuc_moTa = rs.getString(20);
                
                
                list.put(Order_id, new Order(Order_id
                                        , new Cart(cart_id, new User(user_id, username, password, type))
                                        , new Product(maSanPham, tenSanPham, moTa, hinhAnh, donGia, soLuong, new Category(maDanhMuc, tenDanhMuc, danhMucHinhAnh, DanhMuc_moTa)), quantity, createAt));
            }
            d.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    public void AddCartDetails (int user_id, int product_id, int quantity){
        SQLSERVERDataAccess d = null;
        try{
            d = new SQLSERVERDataAccess();  
            d.Execute_StoredProcedures("psAddCartDetails", new Object[]{user_id, product_id, quantity});
        }
        catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void RemoveCartDetails (int user_id, int product_id){
        SQLSERVERDataAccess d = null;
        try{
            d = new SQLSERVERDataAccess();  
            d.Execute_StoredProcedures("psRemoveCartDetails", new Object[]{user_id, product_id});
        }
        catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void EditCartDetails (int user_id, int product_id, int quantity){
        SQLSERVERDataAccess d = null;
        try{
            d = new SQLSERVERDataAccess();  
            d.Execute_StoredProcedures("psEditCartDetails", new Object[]{user_id, product_id, quantity});
        }
        catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void Order (int user_id, int product_id, int quantity){
        SQLSERVERDataAccess d = null;
        try{
            d = new SQLSERVERDataAccess();  
            d.Execute_StoredProcedures("psOrder", new Object[]{user_id, product_id, quantity});
        }
        catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
