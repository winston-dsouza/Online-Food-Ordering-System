/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodyorder;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.CallableStatement;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.SwingUtilities;

/**
 * FXML Controller class
 *
 * @author Winston
 */
public class TakeOrderController implements Initializable {

    TakeOrderModel takeOrderModel =new TakeOrderModel();
    @FXML
    private TableView<ModelTable> table;

    @FXML
    private TableColumn<ModelTable,String> ordernoCol;

    @FXML
    private TableColumn<ModelTable,String> custCol;

    @FXML
    private TableColumn<ModelTable,String> menuCol;

    @FXML
    private TableColumn<ModelTable,String> deliveryCol;

    @FXML
    private TableColumn<ModelTable,String> addressCol;
     @FXML
    private TableColumn<ModelTable, String> QuantityCol;
    
    Connection con;
    
    ObservableList<ModelTable> obList= FXCollections.observableArrayList();
    
    public TakeOrderController(){
        con=SqlConnection.Connector();
    }
    
    @FXML
    private void exitScreen(ActionEvent event){
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(takeOrderModel.isDbConnected()){
             System.out.println("Db connected");
        }else{
             System.out.println("Db not connected");
        }
        
       
        
      
        ordernoCol.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        custCol.setCellValueFactory(new PropertyValueFactory<>("custid"));
        menuCol.setCellValueFactory(new PropertyValueFactory<>("menuname"));
         QuantityCol.setCellValueFactory(new PropertyValueFactory<>("qnt"));
        deliveryCol.setCellValueFactory(new PropertyValueFactory<>("deliverytyp"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
       
        tableConnection();
        table.setItems(obList);
        table.refresh();
        table.getSelectionModel().clearSelection();
       
        
    }
public void tableConnection(){
        
        try {
             
            String query="{CALL `order_list`()}";
            CallableStatement stmt = con.prepareCall(query);
            
            ResultSet rs =stmt.executeQuery(query);
            while(rs.next()){
                obList.add(new ModelTable(rs.getInt("order_id"),rs.getInt("customer_id"),rs.getString("menu_name"),rs.getString("payment_type"), rs.getString("Address"), rs.getInt("Qnt")) );
            }
        } catch (SQLException ex) {
            Logger.getLogger(TakeOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 public void DeliverItem(ActionEvent event){
       ModelTable tableIndex = (ModelTable)table.getSelectionModel().getSelectedItem();
       int tempOrderid = -1;
       try{
       tempOrderid = tableIndex.getOrderid();
       }catch(Exception e){
           infoBox1("no item selected!", null, "Error");
           
       }
   
    if(tempOrderid >= 0){
        String query = "UPDATE orders SET order_status='DELIVERED' WHERE order_id=?";  
        PreparedStatement pst;
           try {              
               pst = con.prepareStatement(query);
               pst.setInt(1, tempOrderid);
               pst.execute();
               infoBox1("order delivered",null, "Success");
               table.getItems().remove(tableIndex);
               table.refresh();
               table.getSelectionModel().clearSelection();
               
               
              
           } catch (SQLException ex) {
               Logger.getLogger(TakeOrderController.class.getName()).log(Level.SEVERE, null, ex);
           }catch(Exception e){
               infoBox1("no item selected!", null, "Error");
           }
               
       
    } else {
        System.out.println("no selction made");
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
