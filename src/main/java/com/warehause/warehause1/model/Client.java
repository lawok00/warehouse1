package com.warehause.warehause1.model;

import jakarta.persistence.*;

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

    public Client(Integer clientId, String clientName, Integer clientNote) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientNote = clientNote;
    }

    public Client() {
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
