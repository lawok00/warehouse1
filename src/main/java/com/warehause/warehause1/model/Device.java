package com.warehause.warehause1.model;

import jakarta.persistence.*;

@Entity
@Table
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Integer deviceId;
    private String deviceProducer;
    private Float devicePrice;
    private String deviceCategory;

    public Device() {
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceProducer() {
        return deviceProducer;
    }

    public void setDeviceProducer(String deviceProducer) {
        this.deviceProducer = deviceProducer;
    }

    public Float getDevicePrice() {
        return devicePrice;
    }

    public void setDevicePrice(Float devicePrice) {
        this.devicePrice = devicePrice;
    }

    public String getDeviceCategory() {
        return deviceCategory;
    }

    public void setDeviceCategory(String deviceCategory) {
        this.deviceCategory = deviceCategory;
    }
}
