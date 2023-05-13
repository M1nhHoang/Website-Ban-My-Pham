/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataContext;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author HP
 */
public class SQLSERVERDataAccess {
    private final String server = "LAPTOP-G9HMKMUF\\SQLEXPRESS";
    private final String user = "sa";
    private final String password = "123456";
    private final String db = "dbQuanLiBanMiPham";
    private final int port = 1433;
    private final String IntegratedSecurity = "IntegratedSecurity=true";
    private final String Encrypt = "Encrypt=false";
    private Connection conn;
    private Statement stm;
    private ResultSet rs;
    private String DriverClass, DriverURL;

    public SQLSERVERDataAccess() throws SQLException  {
        DriverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        DriverURL = "jdbc:sqlserver://"+server+":"+port+";databaseName="+db+";"+IntegratedSecurity+";"+Encrypt;
        try {
            Class.forName(DriverClass);
        } catch (ClassNotFoundException ex) {
//                out.println("500 - "+ex);
        }
        this.conn = DriverManager.getConnection(DriverURL,user,password);
        this.stm = this.conn.createStatement();
    }

    public ResultSet getResultSet(String SQL) {
        try {
            ResultSet rs;
            rs = this.stm.executeQuery(SQL);
            return rs;
        } catch (SQLException ex) {}
            return null;
    }

    public ResultSet getResultSet(String SQL, Object[] param) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(SQL);
            int i = 1;
            for (Object value : param) {
                ps.setObject(i++, value);
            }
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //SQL (DELETE, UPDATE, INSERT)
    public int ExecuteSQL(String SQL) {
        try {
            int k = 0;
            k = this.stm.executeUpdate(SQL);
            return k;
        } catch (SQLException e) {}
        return 0;
    }

    public int ExecuteSQL(String SQL, Object[] param) {
        try {
            int k = 0;
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            int i = 1;
            for (Object value : param)
                ps.setObject(i++, value);
            k = ps.executeUpdate();
        return k;
        } catch (SQLException e) {}
        return 0;
    }

    public int Execute_StoredProcedures(String NameStoredProcedures, Object[] param) throws SQLException {
        String prs = "("+ "?,".repeat(param.length).substring(0, param.length*2-1) +")";
        CallableStatement ps = this.conn.prepareCall("{call " + NameStoredProcedures + prs +"}");
        int i = 1;
        for (Object value : param) {
            ps.setObject(i++, value);
        }
        int k = ps.executeUpdate();

        return k;
    }

    public ResultSet getResultSet_StoredProcedures(String NameStoredProcedures, Object[] param) {
        PreparedStatement ps = null;
        try {
            String prs = (param != null)?"("+ "?,".repeat(param.length).substring(0, param.length*2-1) +")":"";
            ps = conn.prepareCall("{call " + NameStoredProcedures + prs +"}");
            if (param != null) {
                int i = 1;
                for (Object value : param) {
                    ps.setObject(i++, value);
                }
            }
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void close() {
        try {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}