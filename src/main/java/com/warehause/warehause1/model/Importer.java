package com.warehause.warehause1.model;


import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

@Table
@Entity
public class Importer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "impotrer_id")
private Integer importerId;

    private String importerName;
private String importerProducent;

    public Importer() {
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
}

