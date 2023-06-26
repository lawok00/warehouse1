package com.warehause.warehause1.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

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

    public Device(Integer deviceId, String deviceProducer, Float devicePrice, String deviceCategory) {
        this.deviceId = deviceId;
        this.deviceProducer = deviceProducer;
        this.devicePrice = devicePrice;
        this.deviceCategory = deviceCategory;
    }

    public Device() {
    }
    @ManyToMany
    @JoinTable(name = "client_device",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    private List<Client> clientList;

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device device)) return false;
        return Objects.equals(getDeviceId(), device.getDeviceId()) && Objects.equals(getDeviceProducer(), device.getDeviceProducer()) && Objects.equals(getDevicePrice(), device.getDevicePrice()) && Objects.equals(getDeviceCategory(), device.getDeviceCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeviceId(), getDeviceProducer(), getDevicePrice(), getDeviceCategory());
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceId=" + deviceId +
                ", deviceProducer='" + deviceProducer + '\'' +
                ", devicePrice=" + devicePrice +
                ", deviceCategory='" + deviceCategory + '\'' +
                '}';
    }
}
