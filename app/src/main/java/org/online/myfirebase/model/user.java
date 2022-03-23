package org.online.myfirebase.model;

import java.io.Serializable;

public class user implements Serializable {
    public String name;
    public  String password;
    public String role;
    public String key;

    public user(String nama, String pass, String rl) {
        name = nama;
        password = pass;
        role=rl;
    }

    public user(String role) {
        this.role =role;
    }




    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    private String confirmPassword;

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public  String getRole() {
        return role;
    }

    public String getKey() {
        return key;
    }





}
