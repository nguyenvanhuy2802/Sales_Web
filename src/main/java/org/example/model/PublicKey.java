package org.example.model;

import java.sql.Timestamp;

public class PublicKey {
    private int keyId;
    private int userId;
    private String name;
    private Timestamp createTime;
    private Timestamp endTime;
    private String status;
    private Timestamp reportTime;

    public PublicKey(){}

    public PublicKey(int userId,String name) {
        this.userId = userId;
        this.name = name;
    }

    public PublicKey(int keyId, int userId, String name, Timestamp createTime, Timestamp endTime, String status, Timestamp reportTime) {
        this.keyId = keyId;
        this.userId = userId;
        this.name = name;
        this.createTime = createTime;
        this.endTime = endTime;
        this.status = status;
        this.reportTime = reportTime;
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getReportTime() {
        return reportTime;
    }

    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
    }
}
