/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foody;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Winston
 */
public class MenuController implements Initializable {
    public MenuModel menuModel =new MenuModel();
    
    @FXML
    private Label custLabel;
    @FXML
    private TableView<ModelTable> table;
    @FXML
    private TableColumn<ModelTable, String> MenuIdCol;

    @FXML
    private TableColumn<ModelTable, String> MenuNameCol;

    @FXML
    private TableColumn<ModelTable, String> PriceCol;

    @FXML
    private TableColumn<ModelTable, String> QuantityCol;

    @FXML
    private Label totalAmount;
    
    @FXML
    private PasswordField oldpasstxt;

    @FXML
    private PasswordField newpasstxt;
    
    @FXML
    private TextField statetxt;

    @FXML
    private TextField citytxt;

    @FXML
    private TextField pincodetxt;

    @FXML
    private TextArea landtxt;
    @FXML
    private TableView<ModelTable1> table1;

    @FXML
    private TableColumn<ModelTable1, String> OrderidCol1;

    @FXML
    private TableColumn<ModelTable1, String> MenuNameCol1;

    @FXML
    private TableColumn<ModelTable1, String> QuantityCol1;

    @FXML
    private TableColumn<ModelTable1, String> OrderStatusCol1;

    

    /*
    @FXML
    private JFXButton btn1;

    @FXML
    private JFXButton btn2;

    @FXML
    private JFXButton btn3;

    @FXML
    private JFXButton btn4;

    @FXML
    private JFXButton btn5;

    @FXML
    private JFXButton btn6;

    @FXML
    private JFXButton btn7;

    @FXML
    private JFXButton btn8;

    @FXML
    private JFXButton btn9;

    @FXML
    private JFXButton btn10;

    @FXML
    private JFXButton btn11;

    @FXML
    private JFXButton btn12;
    */
    
    Connection con; //connection for table 
    
    public static int i;
    
    boolean type;
    
    
    ObservableList<ModelTable> obList= FXCollections.observableArrayList();
    ObservableList<ModelTable1> obList1= FXCollections.observableArrayList();

    
    public MenuController(){
        con=SqlConnection.Connector();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         totalAmount.setText("0");
        
         i= LoginController.cust_id;//customer id which is primary key
         custLabel.setText(Integer.toString(i));
         if(menuModel.isDbConnected()){
             System.out.println("Db connected");
        }else{
             System.out.println("Db not connected");
        }
         
        
         MenuIdCol.setCellValueFactory(new PropertyValueFactory<>("menuid"));
         MenuNameCol.setCellValueFactory(new PropertyValueFactory<>("menuname"));
         PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
         QuantityCol1.setCellValueFactory(new PropertyValueFactory<>("quantity_item"));
         tableConnection();
         table.setItems(obList);
         table.refresh();
         table.getSelectionModel().clearSelection();
         calculate();
         getAddress();
         
         OrderidCol1.setCellValueFactory(new PropertyValueFactory<>("orderno"));
         MenuNameCol1.setCellValueFactory(new PropertyValueFactory<>("menuname"));  
         QuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity_item"));
         OrderStatusCol1.setCellValueFactory(new PropertyValueFactory<>("status"));
         tableConnection1();
         table1.setItems(obList1);
         table1.refresh();
        
         
    }
       
    
    public void deleteItem(ActionEvent event){
       ModelTable tableIndex = (ModelTable)table.getSelectionModel().getSelectedItem();
       int tempMenuid = -1;
       try{
       tempMenuid = tableIndex.getMenuid();
       }catch(Exception e){
           infoBox1("no item selected!", null, "Error");
           
       }
   
    if(tempMenuid >= 0){
        String query = "DELETE FROM orders WHERE  ( menu_id = ? and customer_id=? and order_status='ADDED_TO_CART') ";  
        PreparedStatement pst;
           try {              
               pst = con.prepareStatement(query);
               pst.setInt(1, tempMenuid);
               pst.setInt(2, i);
               pst.execute();
               table.getItems().remove(tableIndex);
               table.refresh();
               table.getSelectionModel().clearSelection();
               
               calculate();
              
           } catch (SQLException ex) {
               Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
           }catch(Exception e){
               infoBox1("no item selected!", null, "Error");
           }
               
       
    } else {
        System.out.println("no selction made");
    }
}
    
    
    
    
    
    public void tableConnection(){
        table.getItems().clear();
        try {
             
            String query="SELECT menu.price as Price ,menu.menu_id,menu.menu_name as Name,quantity FROM orders JOIN menu ON orders.menu_id=menu.menu_id WHERE orders.customer_id="+i+" and order_status='ADDED_TO_CART'";
            ResultSet rs =con.createStatement().executeQuery(query);
            while(rs.next()){
                obList.add(new ModelTable(rs.getString("Name"), rs.getInt("menu_id"),rs.getInt("quantity"), rs.getInt("Price")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void tableConnection1(){
        
        try {
             
            String query="SELECT orders.order_id as OrderId,menu.menu_name as Name,quantity, order_status FROM orders JOIN menu ON orders.menu_id=menu.menu_id WHERE orders.customer_id="+i+" and (order_status='PAYMENT_CONFIRMED' OR order_status='DELIVERED') ";
            ResultSet rs =con.createStatement().executeQuery(query);
            while(rs.next()){
                obList1.add(new ModelTable1( rs.getInt("OrderId"), rs.getString("Name"), rs.getInt("quantity"),rs.getString("order_status")) );
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void calculate(){
        try {
            
            String query="SELECT sum(menu.price*quantity) as totalamount FROM orders JOIN menu ON orders.menu_id=menu.menu_id WHERE orders.customer_id="+i+" and order_status='ADDED_TO_CART'";
            ResultSet rs =con.createStatement().executeQuery(query);
            while(rs.next()){
             
                int totalamount=rs.getInt("totalamount");
                
                totalAmount.setText(Integer.toString(totalamount));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //code below is load the payment screen
    @FXML
     public void PaymentScreen(ActionEvent event) throws Exception  {
		Stage primaryStage =new Stage();
                primaryStage.initStyle(StageStyle.UNDECORATED);
		Parent root =FXMLLoader.load(getClass().getResource("Payment.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
                
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
	}
     public void Menu1(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(1)){
               type =infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
               // btn1.setDisable(true);
               if(type){
                   menuModel.increment_qnt(1);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
            }else{
                menuModel.cart_add(1);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn1.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu2(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(2)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                //btn2.setDisable(true);
                if(type){
                   menuModel.increment_qnt(2);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
            }else{
                menuModel.cart_add(2);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn2.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu3(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(3)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(3);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                //btn3.setDisable(true);
            }else{
                menuModel.cart_add(3);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn3.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu4(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(4)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(4);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                //btn4.setDisable(true);
            }else{
                menuModel.cart_add(4);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
                //btn4.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu5(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(5)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(5);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                //btn5.setDisable(true);
            }else{
                menuModel.cart_add(5);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn5.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu6(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(6)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(6);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
               // btn6.setDisable(true);
            }else{
                menuModel.cart_add(6);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn6.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu7(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(7)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(7);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                //btn7.setDisable(true);
            }else{
                menuModel.cart_add(7);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn7.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu8(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(8)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(8);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                
                //btn8.setDisable(true);
            }else{
                menuModel.cart_add(8);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn8.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu9(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(9)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(9);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
               // btn9.setDisable(true);
            }else{
                menuModel.cart_add(9);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
                //btn9.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu10(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(10)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(10);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                //btn10.setDisable(true);
            }else{
                menuModel.cart_add(10);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn10.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu11(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(11)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(11);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                //btn11.setDisable(true);
            }else{
                menuModel.cart_add(11);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn11.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu12(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(12)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(12);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                
               // btn12.setDisable(true);
            }else{
                menuModel.cart_add(12);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
                //btn12.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void UpdatePassword(ActionEvent event){
         
        try {
            String oldpass=oldpasstxt.getText();
            String newpass=newpasstxt.getText();
            if(!(oldpass.isEmpty() || newpass.isEmpty())){
                 if(menuModel.check_if_pass_equal_to_old(oldpasstxt.getText())){
                type=infoBox("Do you really want to change the password",null,"Alter!");
                if(type){
                menuModel.update_password(newpasstxt.getText());
                    infoBox1("Password changed sucessfully", null,"Success");
                    oldpasstxt.clear();
                    newpasstxt.clear();
                }
            }else{
                infoBox1("Please enter your old password correctly!!",null,"failed to change password");
            }
            }else{
                infoBox1("please enter the password field", null,"Alert!");
            }
                
                } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }
      public void getAddress(){
        String query="SELECT state, city, landmark, pincode FROM  customer where customer_id="+i;
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst =con.prepareStatement(query);
            rs=pst.executeQuery();
            if(rs.next()){
                statetxt.setText(rs.getString("state"));
                citytxt.setText(rs.getString("city"));
                landtxt.setText(rs.getString("landmark"));
                int pincode=rs.getInt("pincode");
                pincodetxt.setText(Integer.toString(pincode));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void putAddress(ActionEvent event){
        if(!(statetxt.getText().isEmpty()||citytxt.getText().isEmpty()||landtxt.getText().isEmpty()||pincodetxt.getText().isEmpty())){
            type=infoBox("do you really want to update the Address",null,"Alert!");
            if(type){
                    
        String query="Update customer SET state=? ,city=? ,landmark=? ,pincode=? where customer_id="+i;
        PreparedStatement pst;
        try {
            pst =con.prepareStatement(query);
            pst.setString(1,statetxt.getText());
            pst.setString(2,citytxt.getText());
            pst.setString(3,landtxt.getText());
            pst.setInt(4,Integer.parseInt(pincodetxt.getText()));
            pst.execute();
            statetxt.clear();
            citytxt.clear();
            landtxt.clear();
            pincodetxt.clear();
            getAddress();
            infoBox1("Address Sucessfully updated",null,"success");
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
            }else{
                infoBox1("update cancelled",null,"cancelled");
                statetxt.clear();
                citytxt.clear();
                landtxt.clear();
                pincodetxt.clear();
                getAddress();
            }
    }else{
            infoBox1("please fill the address fields", null,"Update failed");
        }
    }
    public void paymentScreen(ActionEvent event){
         if(menuModel.isItemInCart()){
             type=infoBox("Do you really want Confirm order",null,"Alter!");
             if(type){
                 menuModel.update_status_to_confirmed();
                 menuModel.copy_to_payment();
                 try {
                    Node node = (Node)event.getSource();
                    Stage dialogStage = (Stage) node.getScene().getWindow();
                    dialogStage.close();
                    Scene scene;
                    scene = new Scene(FXMLLoader.load(getClass().getResource("Payment.fxml")));
                    dialogStage.setScene(scene);
                    dialogStage.show();
              } catch (IOException ex) {
                     Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
             }else{
                 System.out.println("order is not placed");
             }
         }else{
             infoBox1("No items in the cart",null,"Alert!");
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
