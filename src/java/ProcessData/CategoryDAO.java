/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProcessData;

import DataContext.SQLSERVERDataAccess;
import Entity.Category;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class CategoryDAO {
    
    public CategoryDAO() {
    }
    
    public Hashtable<Integer, Category> getCategory (){
        SQLSERVERDataAccess d = null;
        try{
            d = new SQLSERVERDataAccess();  
        }
        catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        ResultSet rs = d.getResultSet_StoredProcedures("psGetDanhMuc", null);
        Hashtable<Integer, Category> htb = new Hashtable<Integer, Category>();
        try {
            while(rs.next()){
                int maDanhMuc = rs.getInt(1);
                String tenDanhMuc = rs.getString(2);
                String hinhAnh = rs.getString(3);
                String moTa = rs.getString(4);
                
                htb.put(maDanhMuc, new Category(maDanhMuc, tenDanhMuc, hinhAnh, moTa));
            }
            d.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return htb;
    }
}
