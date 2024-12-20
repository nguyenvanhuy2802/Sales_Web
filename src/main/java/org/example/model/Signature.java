package org.example.model;

import java.sql.Timestamp;

public class Signature {
    private int signId;
    private String signValue;
    private int orderId;
    private Timestamp signTime;

    public Signature(String signValue, int orderId) {
        this.signValue = signValue;
        this.orderId = orderId;
    }

    // Constructor
    public Signature(int signId, String signValue, int orderId, Timestamp signTime) {
        this.signId = signId;
        this.signValue = signValue;
        this.orderId = orderId;
        this.signTime = signTime;
    }

    // Getters và Setters
    public int getSignId() {
        return signId;
    }

    public void setSignId(int signId) {
        this.signId = signId;
    }

    public String getSignValue() {
        return signValue;
    }

    public void setSignValue(String signValue) {
        this.signValue = signValue;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getSignTime() {
        return signTime;
    }

    public void setSignTime(Timestamp signTime) {
        this.signTime = signTime;
    }
}
