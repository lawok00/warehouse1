package com.warehause.warehause1.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table
@Entity
public class Importer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "impotrer_id")
    private Integer importerId;
    private String importerName;
    private String importerProducent;

    @OneToOne(mappedBy = "importer")
    @JsonIgnore
    private Seller seller;


    public Importer(Integer importerId, String importerName, String importerProducent) {
        this.importerId = importerId;
        this.importerName = importerName;
        this.importerProducent = importerProducent;
    }

    public Importer() {
    }
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Integer getImporterId() {
        return importerId;
    }

    public void setImporterId(Integer importerId) {
        this.importerId = importerId;
    }

    public String getImporterName() {
        return importerName;
    }

    public void setImporterName(String importerName) {
        this.importerName = importerName;
    }

    public String getImporterProducent() {
        return importerProducent;
    }

    public void setImporterProducent(String importerProducent) {
        this.importerProducent = importerProducent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Importer importer)) return false;
        return Objects.equals(getImporterId(), importer.getImporterId()) && Objects.equals(getImporterName(), importer.getImporterName()) && Objects.equals(getImporterProducent(), importer.getImporterProducent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getImporterId(), getImporterName(), getImporterProducent());
    }

    @Override
    public String toString() {
        return "Importer{" +
                "importerId=" + importerId +
                ", importerName='" + importerName + '\'' +
                ", importerProducent='" + importerProducent + '\'' +
                '}';
    }
}

