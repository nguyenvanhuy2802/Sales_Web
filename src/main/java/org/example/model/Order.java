package org.example.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {
    private int orderId;
    private int customerId;
    private String buyerName;
    private String deliveryAddress;
    private Timestamp orderDate;
    private String status;
    private BigDecimal totalAmount;
    private int isVerified; // 0: chưa thể xác minh, 1: xác minh hợp lệ, 2: xác minh không hợp lệ
    private String hashCode; // Mã băm duy nhất cho đơn hàng

    public Order() {}

    public Order(int customerId, String buyerName, BigDecimal totalAmount, String deliveryAddress, String hashCode) {
        this.customerId = customerId;
        this.buyerName = buyerName;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
        this.hashCode = hashCode;
    }

    public Order(int customerId, String buyerName, Timestamp orderDate, String status, BigDecimal totalAmount, String deliveryAddress, int isVerified, String hashCode) {
        this.customerId = customerId;
        this.buyerName = buyerName;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
        this.isVerified = isVerified;
        this.hashCode = hashCode;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }
}
