/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProcessData;

import DataContext.SQLSERVERDataAccess;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class CartDAO {

    public CartDAO() {
    }
    
    public void create_cart(int user_id){
        SQLSERVERDataAccess d = null;
        try{
            d = new SQLSERVERDataAccess();  
            d.Execute_StoredProcedures("psCreateCart", new Object[]{user_id});
        }
        catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
