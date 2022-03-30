package org.online.myfirebase.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Product implements Serializable {
    @Exclude
    public String price;
    public String key;
    public String name;

    public Product(){

    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return " "+name+"\n" +
                " "+price;

    }
    public Product (String nama ,String harga){
        name =nama;
        price =harga;
    }

}
