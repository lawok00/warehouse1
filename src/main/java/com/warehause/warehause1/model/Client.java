package com.warehause.warehause1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Integer clientId;
    private String clientName;
    private Integer clientNote;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "s_id")
    private Seller seller;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "client_device",
    joinColumns = @JoinColumn(name = "client_id"),
    inverseJoinColumns = @JoinColumn(name = "device_id"))
    private List <Device> deviceList;

    public Client(Integer clientId, String clientName, Integer clientNote) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientNote = clientNote;
    }

    public Client() {
    }
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public List<Device> getDeviceList(){
        return deviceList;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getClientNote() {
        return clientNote;
    }

    public void setClientNote(Integer clientNote) {
        this.clientNote = clientNote;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return Objects.equals(getClientId(), client.getClientId()) && Objects.equals(clientName, client.clientName) && Objects.equals(clientNote, client.clientNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientId(), clientName, clientNote);
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clientNote=" + clientNote +
                '}';
    }
}
