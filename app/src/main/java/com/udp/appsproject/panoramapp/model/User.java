package com.udp.appsproject.panoramapp.model;

public class User {
    public String name;
    public String lastName;
    public String userName;
    public String email;

    public User (){
    }

    public User (String name, String lastName, String userName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}