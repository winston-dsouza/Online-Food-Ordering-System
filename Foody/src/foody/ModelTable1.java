/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foody;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Winston
 */
public class ModelTable1 {
     private final SimpleIntegerProperty orderno;
    private final SimpleStringProperty menuname;
    private final SimpleIntegerProperty quantity_item;
    private final SimpleStringProperty status;

    public ModelTable1(int orderno, String menuname, int quantity_item,String status) {
        this.menuname = new SimpleStringProperty(menuname);
        this.orderno = new SimpleIntegerProperty(orderno);
        this.quantity_item = new SimpleIntegerProperty(quantity_item);
        this.status = new SimpleStringProperty(status);
    }

    public String getMenuname() {
        return menuname.get();
    }

    public void setMenuname(String menuname) {
        this.menuname.set(menuname);
    }

    public int getOrderno() {
        return orderno.get();
    }

    public void setOrderno(int menuid) {
        this.orderno.set(menuid); 
    }

    public int getQuantity_item() {
        return quantity_item.get();
    }

    public void setQuantity_item(int quantity_item) {
        this.quantity_item.set(quantity_item);
    }

    public String getStatus() {
        return status.get();
    }

    public void setPrice(String status) {
        this.status.set(status);
    }

    
}
