package org.example.model;

import java.sql.Timestamp;

public class User {
    private int userId;
    private String name;
    private String email;
    private String phone;
    private String username;
    private String address;
    private String password;
    private String role;
    private String profileImage;
    private Timestamp createdAt;

    public User() {
    }

    public User(String name, String email, String phone, String username,  String address, String password, String role, String profileImage) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.address = address;
        this.password = password;
        this.role = role;
        this.profileImage = profileImage;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
