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
    private Integer keyId;
    private String hashCode;

    public Order() {}

    public Order(int customerId, String buyerName, BigDecimal totalAmount, String deliveryAddress, String hashCode) {
        this.customerId = customerId;
        this.buyerName = buyerName;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
        this.hashCode = hashCode;
    }

    public Order(int customerId, String buyerName, Timestamp orderDate, String status, BigDecimal totalAmount,
                 String deliveryAddress, Integer keyId, String hashCode) {
        this.customerId = customerId;
        this.buyerName = buyerName;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
        this.keyId = keyId;
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

    public Integer getKeyId() {
        return keyId;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }
}
