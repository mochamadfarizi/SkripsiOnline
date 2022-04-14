package org.online.myfirebase.model;

import java.io.Serializable;

public class Cart implements Serializable {
    private String buyer;
    private String key;
    private String cartName;
    private String cartPrice;
    private String cartQuantity;

    public Cart(){

    }

    public Cart(String user,String nama, String harga,String Quantyty){
        buyer=user;
        cartName =nama;
        cartPrice =harga;
        cartQuantity=Quantyty;
    }

    public String getUsername() {
        return buyer;
    }

    public void setUsername(String username) {
        this.buyer= username;
    }

    public String getProductName() {
        return cartName;
    }

    public void setProductName(String productName) {
        this.cartName = productName;
    }

    public String getProductPrice() {
        return cartPrice;
    }

    public void setProductPrice(String productPrice) {
        this.cartPrice = productPrice;
    }

    public String getProductQuantity() {
        return cartQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.cartQuantity = productQuantity;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString() {
        return " "+buyer+"\n" +
                " "+cartName+"\n"+
                " "+cartPrice+"\n"+
                " "+cartQuantity;


    }
}
