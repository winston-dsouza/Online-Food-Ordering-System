/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foody;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Winston
 */
public class SignupController implements Initializable {
   public SignupModel signupModel= new SignupModel();
    @FXML
    private JFXTextField textfname;

    @FXML
    private JFXTextField textlname;

    @FXML
    private JFXTextField textemail;

    @FXML
    private JFXPasswordField textpass;

    @FXML
    private JFXTextField textnumber;

    @FXML
    private JFXTextField textstate;

    @FXML
    private JFXTextField textcity;

    @FXML
    private JFXTextField textland;

    @FXML
    private JFXTextField textpin;
    @FXML
    private JFXPasswordField textpassconf;
    
    
    
    @FXML
    private Label alertLabel;
    private int pin;
   
    
   @FXML
   private void exitScreen(ActionEvent event){
        System.exit(0);
    }
   
    public static boolean isValid(String iemail) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (iemail == null) 
            return false; 
        return pat.matcher(iemail).matches(); 
    } 
   
     @FXML
     public void loginScreen(ActionEvent event) throws Exception  {
		Stage primaryStage =new Stage();
                primaryStage.initStyle(StageStyle.UNDECORATED);
		Parent root =FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
                
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
	}

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(signupModel.isDbConnected()){
            System.out.println("connected");
        }else{
            System.out.println("not connected");
        }
    }  
    @FXML
    public void Signup(ActionEvent event){
       
        String fname=textfname.getText();
        String lname=textlname.getText();
        String  email=textemail.getText().toLowerCase();
        String pass=textpass.getText();
        
        String phone=textnumber.getText();
        String state=textstate.getText().toLowerCase();
        String city=textcity.getText().toLowerCase();
        String landmark=textland.getText().toLowerCase();
        
        try{
         pin=Integer.parseInt(textpin.getText());
        }catch(NumberFormatException ex){
            alertLabel.setText("Please fill blank fields");
        }
        
       try {
           if((fname.isEmpty()|| lname.isEmpty() || email.isEmpty() || pass.isEmpty() || phone.isEmpty() || state.isEmpty() || city.isEmpty() || landmark.isEmpty() ||(textpin.getText()).isEmpty() )){
              alertLabel.setText("Please fill blank fields");
             
           } else if(!((lname).matches("[a-zA-Z]+"))){
               alertLabel.setText("invalid name format");
              
           }
           else if(!((fname).matches("[a-zA-Z]+"))){
               alertLabel.setText("invalid name format");
           }else if(!(isValid(email))){
               alertLabel.setText("invalid email");
           }else if(!(pass.length()>=4)){
               alertLabel.setText("Password length should be more than 4");
           }else if(!(phone.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]"))){
               alertLabel.setText("invalid phone number");
           }else if(!((state).matches("[a-zA-Z]+"))){
               alertLabel.setText("invalid state field");
           }else if(!((city).matches("[a-zA-Z]+"))){
               alertLabel.setText("invalid state field");
           }else if (!(Integer.toString(pin).matches("[0-9][0-9][0-9][0-9][0-9][0-9]"))){
               alertLabel.setText("invalid picode");
           }else if(signupModel.isEmail(email)){
               alertLabel.setText("email already present");
               return;
           }
               else{
               alertLabel.setText("");
               signupModel.isSignup(fname, lname, email, pass, phone, state, city, landmark, pin); 
               alertLabel.setText("Signup Scucessfull");
               infoBox("Signup Successfull",null,"Success" );
               Node node = (Node)event.getSource();
               Stage dialogStage = (Stage) node.getScene().getWindow();
               dialogStage.close();
               Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
               dialogStage.setScene(scene);
               dialogStage.show();
           }
           
       } catch (SQLException ex) {
           alertLabel.setText("Please fill balnk fields");
           Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
       }catch(IOException ex){
           System.out.println(""+ex);
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
