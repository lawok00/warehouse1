package com.warehause.warehause1.model;

import jakarta.persistence.*;

@Entity
@Table

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Integer clientId;
    private String clientName;
    private Integer clientNote;

    public Client() {
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
