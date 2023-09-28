package com.warehause.warehause1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Integer sellerId;
    private String sellerName;
    private String sellerLevel;
    private Integer sellerAge;


    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "i_id")
    private Importer importer;

    @OneToMany(mappedBy = "seller")

    private List<Client> clients;
    public Seller() {
    }

    public Seller(Integer sellerId, String sellerName, String sellerLevel, Integer sellerAge) {
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.sellerLevel = sellerLevel;
        this.sellerAge = sellerAge;
    }

    public Importer getImporter() {
        return importer;
    }

    public void setImporter(Importer importer) {
        this.importer = importer;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerLevel() {
        return sellerLevel;
    }

    public void setSellerLevel(String sellerLevel) {
        this.sellerLevel = sellerLevel;
    }

    public Integer getSellerAge() {
        return sellerAge;
    }

    public void setSellerAge(Integer sellerAge) {
        this.sellerAge = sellerAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller seller)) return false;
        return Objects.equals(getSellerId(), seller.getSellerId()) && Objects.equals(getSellerName(), seller.getSellerName()) && Objects.equals(getSellerLevel(), seller.getSellerLevel()) && Objects.equals(getSellerAge(), seller.getSellerAge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSellerId(), getSellerName(), getSellerLevel(), getSellerAge());
    }

    @Override
    public String toString() {
        return "Seller{" +
                "sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                ", sellerLevel='" + sellerLevel + '\'' +
                ", sellerAge=" + sellerAge +
                '}';
    }
}

