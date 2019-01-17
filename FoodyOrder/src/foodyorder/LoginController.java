/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodyorder;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Winston
 */
public class LoginController implements Initializable {
    LoginModel loginModel =new LoginModel();
    @FXML
    private JFXTextField emptxt;

    @FXML
    private JFXPasswordField passtxt;
    private int empid;
    
    
    public void exitScreen(ActionEvent event){
        System.exit(0);
    }
    
    @FXML
     public void TakeOrderScreen(ActionEvent event) throws Exception  {
		Stage primaryStage =new Stage();
                primaryStage.initStyle(StageStyle.UNDECORATED);
		Parent root =FXMLLoader.load(getClass().getResource("TakeOrder.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
                
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
	}
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         if(loginModel.isDbConnected()){
             System.out.println("Db connected");
        }else{
             System.out.println("Db not connected");
        }
    }    
    public void Login(ActionEvent event) {
     try {

        
         try{
          empid=Integer.parseInt(emptxt.getText());
          
         }catch(NumberFormatException e){
             System.out.println("enter correct id and pass");
         }
         String emppass=passtxt.getText();
         
            if(loginModel.isLogin(empid,emppass )){
                if(loginModel.isAdmin(empid, emppass)){
                    infoBox("Login Successfull As Admin",null,"Success" );
                
                Node node = (Node)event.getSource();
                Stage primaryStage =new Stage();
                Stage dialogStage = (Stage) node.getScene().getWindow();
                dialogStage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AdminPanel.fxml")));
		primaryStage.setScene(scene);
                dialogStage.setScene(scene);
                dialogStage.show();
                }else{
                 infoBox("Login Successfull as Employee",null,"Success" );
                 Node node = (Node)event.getSource();
                Stage primaryStage =new Stage();
                Stage dialogStage = (Stage) node.getScene().getWindow();
                dialogStage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("TakeOrder.fxml")));
		primaryStage.setScene(scene);
                dialogStage.setScene(scene);
                dialogStage.show();
                } 
            }else{
                infoBox("Enter correct id and password",null,"Failed" );
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
             infoBox("Enter correct id and password",null,"Failed" );
        }
        
    }
     public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
   
    
}
