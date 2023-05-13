/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProcessData;

import DataContext.SQLSERVERDataAccess;
import Entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class UserDAO {

    public UserDAO() {
    }
    
    public User login(String username){
        SQLSERVERDataAccess d = null;
        try{
            d = new SQLSERVERDataAccess();  
        }
        catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        }  
        ResultSet rs = d.getResultSet_StoredProcedures("psGetUserAccount", new Object[]{username});
        User userAcc = null;
        try {
            d = new SQLSERVERDataAccess();  
            while(rs.next()){
                int user_id = rs.getInt(1);
                String user = rs.getString(2);
                String password = rs.getString(3);
                String type = rs.getString(4);
                userAcc = new User(user_id, user, password, type);
            }
            d.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userAcc;
    }
    
    public void register(String user, String pass) {
        try {
            SQLSERVERDataAccess d = null;
            try {
                d = new SQLSERVERDataAccess();
            } catch (Exception e) {
            }
            d.Execute_StoredProcedures("psRegisterUserAccount", new Object[]{user, pass});
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User checkExistAccount(String user) {
        SQLSERVERDataAccess d = null;
        try {
            d = new SQLSERVERDataAccess();
        } catch (SQLException e) {
        }
        ResultSet rs = d.getResultSet_StoredProcedures("psGetUserAccount", new Object[]{user});
        try {
            while (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
            d.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
