/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foody;

import com.jfoenix.controls.JFXRadioButton;
import static foody.MenuController.i;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Winston
 */
public class PaymentController implements Initializable {
     public PaymentModel paymentModel =new PaymentModel();
     /*
    @FXML
    private JFXRadioButton onlinepaybtn;

    @FXML
    private ToggleGroup payment;

    @FXML
    private JFXRadioButton codbtn;
     */
    
    @FXML
    private TextField cardnumtxt;

    @FXML
    private TextField cardholdertxt;

    @FXML
    private TextField monthtxt;

    @FXML
    private TextField yeartxt;

    @FXML
    private PasswordField cvvtxt;
    @FXML
    private Label amounttxt;
    @FXML
    private Label warning;

    
    Connection con ;
   // public static int ze;
    
      public PaymentController(){
       con = SqlConnection.Connector();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       i= LoginController.cust_id;//customer id which is primary key
         //ze= MenuController.i;
         System.out.println(""+i);
        
         if(paymentModel.isDbConnected()){
             System.out.println("Db connected");
        }else{
             System.out.println("Db not connected");
        }
         calculate();
         
    }    
     public void calculate(){
        try {
            
            String query="SELECT sum(menu.price*quantity) as totalamount FROM orders JOIN menu ON orders.menu_id=menu.menu_id WHERE orders.customer_id="+i+" and order_status='CONFIRMED'";
            ResultSet rs =con.createStatement().executeQuery(query);
            while(rs.next()){
             
                int totalamount=rs.getInt("totalamount");
                
                amounttxt.setText(Integer.toString(totalamount));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    @FXML
     public void ConfirmedScreen(ActionEvent event) throws Exception  {
         
		Stage primaryStage =new Stage();
                primaryStage.initStyle(StageStyle.UNDECORATED);
		Parent root =FXMLLoader.load(getClass().getResource("Confirmed.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
                
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
	}
    public void cashOnDelivery(ActionEvent event){
        cardnumtxt.setDisable(true);
        cardholdertxt.setDisable(true);
        cvvtxt.setDisable(true);
        monthtxt.setDisable(true);
        yeartxt.setDisable(true);
        String query= "UPDATE payment SET payment.payment_type='CASH_ON_DELIVERY' WHERE payment.payment_status='NOT_CONFIRMED' and order_id IN(SELECT order_id from orders WHERE orders.order_id=payment.order_id and customer_id=?)";
        PreparedStatement pst;
         try {
             pst =con.prepareStatement(query);
             pst.setInt(1, i);
             pst.execute();
         } catch (SQLException ex) {
             Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
         }
            
    }
    public void onlinePayment(ActionEvent event){
        cardnumtxt.setDisable(false);
        cardholdertxt.setDisable(false);
        cvvtxt.setDisable(false);
        monthtxt.setDisable(false);
        yeartxt.setDisable(false);
        String query="UPDATE payment SET payment.payment_type='ONLINE_PAYMENT' WHERE payment.payment_status='NOT_CONFIRMED' and order_id IN(SELECT order_id from orders WHERE orders.order_id=payment.order_id and customer_id=?)";
         PreparedStatement pst;
         try {
             pst =con.prepareStatement(query);
             pst.setInt(1, i);
             pst.execute();
         } catch (SQLException ex) {
             Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    public void confirmPayment(ActionEvent event){
         try {
             if(paymentModel.isPaymentOnline()){
                 if(!(cardnumtxt.getText().isEmpty()||cardholdertxt.getText().isEmpty()||cvvtxt.getText().isEmpty()||monthtxt.getText().isEmpty()||yeartxt.getText().isEmpty())){
                     
                         
                     if(!(cardnumtxt.getText().length()==16)){
                         warning.setText("enter valid card numder");
                         return;
                     }else if(!(cvvtxt.getText().length()==3)){
                         warning.setText("enter valid CVV");
                         return;
                     }else if(!((monthtxt.getText()).matches("0?[1-9]|1[012]"))){
                         warning.setText("Enter valid month");
                         return;
                     }else if(!((yeartxt.getText()).matches("1[8-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9]"))){
                         warning.setText("Enter valid year");
                         return;
                     }else{
                     warning.setText("");
                     String card=cardnumtxt.getText();
                     String cardh=cardholdertxt.getText();
                     int cvvpass=Integer.parseInt(cvvtxt.getText());
                     int month=Integer.parseInt(monthtxt.getText());
                     int year=Integer.parseInt(yeartxt.getText());
                     paymentModel.insert_payment_details(card,cardh ,cvvpass ,month,year);
                     paymentModel.update_payment_status_to_confirmed();
                     paymentModel.update_status_to_payment_confirmed();
                     infoBox1("Payment Sucessfull", null, "sucess");
                     }
                     
                     try {
                         Node node = (Node)event.getSource();
                         Stage dialogStage = (Stage) node.getScene().getWindow();
                         dialogStage.close();
                         Scene scene;
                         scene = new Scene(FXMLLoader.load(getClass().getResource("Confirmed.fxml")));
                         dialogStage.setScene(scene);
                         dialogStage.show();
                     } catch (IOException ex) {
                         Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
                     }
                    
                 }else{
                     infoBox1("enter the payment details",null,"Alert!");
                 }
             }else{
                 paymentModel.update_payment_status_to_confirmed();
                 paymentModel.update_status_to_payment_confirmed();
                 infoBox1("Order Confirmed",null, "Success");
                  try {
                         Node node = (Node)event.getSource();
                         Stage dialogStage = (Stage) node.getScene().getWindow();
                         dialogStage.close();
                         Scene scene;
                         scene = new Scene(FXMLLoader.load(getClass().getResource("Confirmed.fxml")));
                         dialogStage.setScene(scene);
                         dialogStage.show();
                     } catch (IOException ex) {
                         Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
                     }
             }
         } catch (SQLException ex) {
             Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
         public static boolean infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.getButtonTypes();
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK button
         return true;
        } else {
        // ... user chose CANCEL or closed the dialog
        return false;
        }
        
    }
     
     public static void infoBox1(String infoMessage, String headerText, String title){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle(title);
         alert.setHeaderText(headerText);
         alert.setContentText(infoMessage);
         alert.showAndWait();
     }
     public  void Logout(ActionEvent event){
         System.exit(0);
     }
   
}
