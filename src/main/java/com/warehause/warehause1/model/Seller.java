package com.warehause.warehause1.model;

import jakarta.persistence.*;

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

    public Seller() {
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
}
