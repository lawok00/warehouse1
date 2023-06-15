package com.warehause.warehause1.service;

import com.warehause.warehause1.model.Device;
import com.warehause.warehause1.model.Importer;
import com.warehause.warehause1.repository.ImporterJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImporterService {
    private final ImporterJpaRepository importerJpaRepository;

    public ImporterService(ImporterJpaRepository importerJpaRepository) {
        this.importerJpaRepository = importerJpaRepository;
    }
    public Iterable<Importer> getAllImporters(){
        return importerJpaRepository.findAll();
    }
    public Optional<Importer> getImporterById(int id){
        return importerJpaRepository.findById(id);
    }

    public Optional <Importer> saveNew(Importer importer) {
        if (importer.getImporterId() != null && importerJpaRepository.existsById(importer.getImporterId())) {
            return Optional.empty();
        } else {
            return Optional.of(importerJpaRepository.save(importer));
        }
    }
    public void removeById(int id){
        importerJpaRepository.deleteById(id);
    }

    public Optional<Importer> fulImporterUpdate(Integer importerId, Importer updateImporter){
        if(importerJpaRepository.existsById(importerId)){
            updateImporter.setImporterId(importerId);
            return Optional.of(importerJpaRepository.save(updateImporter));
        }else{
            return Optional.empty();
        }
    }
}
