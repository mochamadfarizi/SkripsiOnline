package org.online.myfirebase.model;

import java.io.Serializable;

public class Cart implements Serializable {
    private int id;
    private String username;
    private int key;
    private String cartName;
    private String cartPrice;
    private int cartQuantity;

    public Cart(){

    }

    public Cart(String nama, String harga,int Quantyty){
        cartName =nama;
        cartPrice =harga;
        cartQuantity=Quantyty;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getProductId() {
        return key;
    }

    public void setProductId(int productId) {
        this.key = productId;
    }

    public int getProductQuantity() {
        return cartQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.cartQuantity = productQuantity;
    }

    public String toString() {
        return " "+cartName+"\n" +
                " "+cartPrice+"\n"+
                " "+cartQuantity+"\n"+
                " "+username;

    }
}
