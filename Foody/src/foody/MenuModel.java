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
public class MenuModel {
    Connection connection;
    public MenuModel(){
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
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error");
            return false;
            
        }
        
    }
    public boolean check_if_added_to_cart(int menuId) throws SQLException{
        PreparedStatement preparedStatement = null ;
        ResultSet resultSet =null;
        String query="Select * from orders where menu_id=? and customer_id= ? and order_status='ADDED_TO_CART'";
        
        try {
            preparedStatement =connection.prepareStatement(query);
            preparedStatement.setInt(1,menuId);
            preparedStatement.setInt(2, MenuController.i);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException ex) {
            
            System.out.println(""+ex);
            return false;
        }finally{
            preparedStatement.close();
            resultSet.close();
        }
            
        
        
        
    }
    public void cart_add(int menuId) throws SQLException{
        PreparedStatement preparedStatement = null ;
        String query="insert into orders(customer_id,menu_id,order_status)"+"values(?,?,'ADDED_TO_CART')";
        try {
            preparedStatement =connection.prepareStatement(query);
            preparedStatement.setInt(1, MenuController.i);
            preparedStatement.setInt(2, menuId);
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            preparedStatement.close();
        }
        
    }
    public void increment_qnt(int menuId) throws SQLException{
        PreparedStatement preparedStatement = null ;
        String query="update orders set quantity=quantity+1 WHERE customer_id=? and order_status='ADDED_TO_CART' and menu_id=?";
        try {
            preparedStatement =connection.prepareStatement(query);
            preparedStatement.setInt(1, MenuController.i);
            preparedStatement.setInt(2, menuId);
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            preparedStatement.close();
        }
    }
    
    public boolean check_if_pass_equal_to_old(String oldpass) throws SQLException{
         PreparedStatement preparedStatement = null ;
         ResultSet resultSet =null;
        String query="select * from customer where customer_id=? and password=?";
           try {
            preparedStatement =connection.prepareStatement(query);
            preparedStatement.setInt(1, MenuController.i);
            preparedStatement.setString(2, oldpass);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
             return true;  
             }else{
                  return false;
                }
                
            
        } catch (SQLException ex) {
            
            System.out.println(""+ex);
            return false;
        }finally{
            preparedStatement.close();
            resultSet.close();
        }
        
    }
    public void update_password(String newpass){
         PreparedStatement preparedStatement ;
         String query="update customer SET password =? where customer_id=?";
         
        try {
            preparedStatement =connection.prepareStatement(query);
            preparedStatement.setString(1, newpass);
            preparedStatement.setInt(2, MenuController.i);
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
    public void update_status_to_confirmed(){
        PreparedStatement preparedStatement;
        String query="update orders set order_status='CONFIRMED' where order_status='ADDED_TO_CART' and customer_id= ?";
        try {
            preparedStatement =connection.prepareStatement(query);
            preparedStatement.setInt(1, MenuController.i);
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean isItemInCart(){
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query="select * from orders where order_status='ADDED_TO_CART' and customer_id=?";
        try {
            preparedStatement =connection.prepareStatement(query);
            preparedStatement.setInt(1, MenuController.i);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
             return true;  
             }else{
               return false;
             }
                
            
           
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
        
    }
   public void copy_to_payment(){
        PreparedStatement preparedStatement;
        String query="INSERT into payment(order_id) SELECT orders.order_id FROM orders where orders.order_status='CONFIRMED'";
        try {
            preparedStatement =connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
}
