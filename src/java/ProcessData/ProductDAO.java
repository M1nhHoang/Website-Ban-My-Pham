/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProcessData;

import DataContext.SQLSERVERDataAccess;
import Entity.Category;
import Entity.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class ProductDAO {
    public ProductDAO(){
    }
    
    public Hashtable<Integer, Product> getProducts (String _maDanhMuc){
        SQLSERVERDataAccess d = null;
        try{
            d = new SQLSERVERDataAccess();  
        }
        catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        ResultSet rs = d.getResultSet_StoredProcedures("psGetSanPham", new Object[]{_maDanhMuc});
        Hashtable<Integer, Product> htb = new Hashtable<Integer, Product>();
        try {
            while(rs.next()){
                int maSanPham = rs.getInt(1);
                String tenSanPham = rs.getString(2);
                String moTa = rs.getString(3);
                String hinhAnh = rs.getString(4);
                Double donGia = rs.getDouble(5);
                int soLuong = rs.getInt(6);
                int maDanhMuc = rs.getInt(7);
                String tenDanhMuc = rs.getString(9);
                String danhMucHinhAnh = rs.getString(10);
                String danhMucMoTa = rs.getString(11);
                
                htb.put(maSanPham, new Product(maSanPham, tenSanPham, moTa, hinhAnh, donGia, soLuong, new Category(maDanhMuc, tenDanhMuc, danhMucHinhAnh, danhMucMoTa)));
            }
            d.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return htb;
    }
    
    public void addProduct (String ten, double donGia, int soLuong, String hinhAnh, String moTa, int maDanhMuc){
        SQLSERVERDataAccess d = null;
        try{
            d = new SQLSERVERDataAccess();  
            d.Execute_StoredProcedures("psThemSanPham", new Object[]{ten, donGia, soLuong, hinhAnh, moTa, maDanhMuc});
        }
        catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void editProduct (int maSp, String ten, double donGia, int soLuong, String hinhAnh, String moTa, int maDanhMuc){
        SQLSERVERDataAccess d = null;
        try{
            d = new SQLSERVERDataAccess();  
            d.Execute_StoredProcedures("psSuaSanPham", new Object[]{maSp, ten, donGia, soLuong, hinhAnh, moTa, maDanhMuc});
        }
        catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void deleteProduct (int maSanPham){
        SQLSERVERDataAccess d = null;
        try{
            d = new SQLSERVERDataAccess();  
            d.Execute_StoredProcedures("psXoaSanPham", new Object[]{maSanPham});
        }
        catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
