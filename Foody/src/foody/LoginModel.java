/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foody;

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
    static int customer_id;
    
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
            System.out.println("error");
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error");
            return false;
            
        }
        
    } 
    public boolean isLogin(String email ,String pass) throws SQLException{
        //int customer_id;
        PreparedStatement preparedStatement = null ;
        ResultSet resultSet =null;
        String query="select customer_id from customer where email_id=? and password=?";
        try{
            preparedStatement =connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                customer_id =resultSet.getInt("customer_id");
                System.out.println(""+customer_id);
                LoginController.CustomerId(customer_id);
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
