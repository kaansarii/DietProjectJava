package com.example.dietapp.dtos;

import com.google.gson.annotations.SerializedName;

public class RegisterDto {
    public RegisterDto(String name, String surname, String userName, String email, String password, String userRole) {
        this.name = name;
        this.surname = surname;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @SerializedName("name")
    private String name;
    @SerializedName("surname")
    private   String surname;
    @SerializedName("userName")
    private   String userName;
    @SerializedName("email")
    private   String email;
    @SerializedName("password")
    private   String password;
    @SerializedName("userRole")
    private   String userRole;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
