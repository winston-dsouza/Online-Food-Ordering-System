/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodyorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Winston
 */
public class LoginModel {
     
    
    Connection connection;
    public LoginModel(){
        connection =SqlConnection.Connector();
        if(connection==null){
            System.exit(0);
            System.out.println("notconnected");
        }
    }
    public boolean isDbConnected(){
        try {
            return !connection.isClosed();
        } catch (SQLException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error");
            return false;
            
        }
        
    } 
    public boolean isLogin(int userid,String pass) throws SQLException{
        PreparedStatement preparedStatement = null ;
        ResultSet resultSet =null;
        String query="select * from restaurant where restaurant_id=? and password=?";
        try{
            preparedStatement =connection.prepareStatement(query);
            preparedStatement.setInt(1, userid);
            preparedStatement.setString(2, pass);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println(" no!"+e);
            return false;
        }finally{
            preparedStatement.close();
            resultSet.close();
        }
        
        
        
    }
     public boolean isAdmin(int userid,String pass) throws SQLException{
        PreparedStatement preparedStatement = null ;
        ResultSet resultSet =null;
        String query="select * from restaurant where restaurant_id=? and password=? and designation='ADMIN'";
        try{
            preparedStatement =connection.prepareStatement(query);
            preparedStatement.setInt(1, userid);
            preparedStatement.setString(2, pass);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println(" no!"+e);
            return false;
        }finally{
            preparedStatement.close();
            resultSet.close();
        }
        
        
        
    }
    
    
}
