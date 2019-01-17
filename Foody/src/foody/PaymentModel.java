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
public class PaymentModel {
     Connection connection;
    public PaymentModel(){
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
            Logger.getLogger(PaymentModel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error");
            return false;
            
        }
    }
    public boolean isPaymentOnline() throws SQLException{
        PreparedStatement preparedStatement =null ;
        ResultSet resultSet=null ;
        String query="Select * from payment where payment_status='NOT_CONFIRMED' and payment_type='ONLINE_PAYMENT' and order_id IN (SELECT order_id from orders where orders.order_id=payment.order_id and customer_id=?)";       
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
            
            System.out.println(""+ex);
            return false;
        } finally{
            if(preparedStatement !=null){
            preparedStatement.close();
            }
            if(resultSet!=null){
            resultSet.close();
            }
        }
        
    }
    
    public void update_payment_status_to_confirmed() throws SQLException{
        PreparedStatement preparedStatement =null;
        String query="update payment SET payment.payment_status='CONFIRMED' where order_id IN(select order_id from orders where orders.order_id=payment.order_id and customer_id=?)";
         try {
             preparedStatement =connection.prepareStatement(query);
             preparedStatement.setInt(1, MenuController.i);
             preparedStatement.execute();
         } catch (SQLException ex) {
             Logger.getLogger(PaymentModel.class.getName()).log(Level.SEVERE, null, ex);
         }finally{
             if(preparedStatement!=null){
                preparedStatement.close();
             }
            
        }
    }
    public void insert_payment_details(String cardnumber,String cardholdername,int cvv,int month,int year) throws SQLException{
        PreparedStatement preparedStatement=null;
        String query="INSERT INTO payment_details(customer_id,card_number,card_holder_name,cvv,exp_month,exp_year) VALUES(?,?,?,?,?,?)";
         try {
             preparedStatement =connection.prepareStatement(query);
             preparedStatement.setInt(1, MenuController.i);
             preparedStatement.setString(2,cardnumber);
             preparedStatement.setString(3, cardholdername);
             preparedStatement.setInt(4, cvv);
             preparedStatement.setInt(5,month);
             preparedStatement.setInt(6,year);
             preparedStatement.execute();
         } catch (SQLException ex) {
             Logger.getLogger(PaymentModel.class.getName()).log(Level.SEVERE, null, ex);
         }finally{
            preparedStatement.close();
          
        }
    }
    public void update_status_to_payment_confirmed(){
        PreparedStatement preparedStatement;
        String query="update orders set order_status='PAYMENT_CONFIRMED' where order_status='CONFIRMED' and customer_id= ?";
        try {
            preparedStatement =connection.prepareStatement(query);
            preparedStatement.setInt(1, MenuController.i);
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
