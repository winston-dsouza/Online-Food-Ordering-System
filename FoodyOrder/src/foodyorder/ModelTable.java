/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodyorder;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Winston
 */
public class ModelTable {

    

   
    private final  SimpleIntegerProperty orderid;
    private final  SimpleIntegerProperty custid;
    private final  SimpleStringProperty menuname;
    private final  SimpleStringProperty deliverytyp;
    private final  SimpleStringProperty address;
    private final  SimpleIntegerProperty qnt;
    
    public ModelTable(int orderid,int custid, String menuname,String deliverytyp, String address,int qnt) {
        this.orderid = new SimpleIntegerProperty(orderid);
        this.custid =new SimpleIntegerProperty(custid) ;
        this.menuname = new SimpleStringProperty(menuname) ;
        this.deliverytyp =new SimpleStringProperty(deliverytyp);
        this.address = new SimpleStringProperty(address);
        this.qnt=new SimpleIntegerProperty(qnt);
    }

    public int getQnt() {
        return qnt.get();
    }
    public void setQnt(int qnt) {
        this.qnt.set(qnt);
    }

   

    public int getOrderid() {
        return orderid.get();
    }

    public void setOrderid(int orderid) {
        this.orderid.set(orderid);
    }

    public int getCustid() {
        return custid.get();
    }

    public void setCustid(int custid) {
        this.custid.set(custid);
    }
     

    public String getMenuname() {
        return menuname.get();
    }

    public void setMenuname(String menuname) {
        this.menuname.set(menuname);
    }

    public String getDeliverytyp() {
        return deliverytyp.get();
    }

    public void setDeliverytyp(String deliverytyp) {
        this.deliverytyp.set(deliverytyp);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }
    

       
    
  
    
    

}
