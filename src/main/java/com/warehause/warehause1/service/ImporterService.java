package com.warehause.warehause1.service;

import com.warehause.warehause1.exceptionHandler.NotFoundException;
import com.warehause.warehause1.model.Importer;
import com.warehause.warehause1.model.Seller;
import com.warehause.warehause1.repository.ImporterJpaRepository;
import com.warehause.warehause1.repository.SellerJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImporterService {
    private final ImporterJpaRepository importerJpaRepository;
    private final SellerJpaRepository sellerJpaRepository;

    public ImporterService(ImporterJpaRepository importerJpaRepository, SellerJpaRepository sellerJpaRepository) {
        this.importerJpaRepository = importerJpaRepository;
        this.sellerJpaRepository = sellerJpaRepository;
    }

    public Importer getImporterById(int id){
        Optional<Importer> optionalImporter = importerJpaRepository.findById(id);
        if (optionalImporter.isEmpty()) {
            throw new NotFoundException("Nie ma takiego importera");
        }
        return optionalImporter.get();
    }
    public Iterable<Importer> getAllImporters() {
        return importerJpaRepository.findAll();
    }

    public Importer saveNew(Importer importer) {
        if (importer.getImporterId() != null && importerJpaRepository.existsById(importer.getImporterId())) {
            throw new NotFoundException("Importer o podanym Id już istnieje");
        } else {
            return importerJpaRepository.save(importer);
        }
    }
    public void deleteImporterById(int importerId) {
        Optional<Importer> importerToDelete = importerJpaRepository.findById(importerId);
        if (importerToDelete.isPresent()) {
            Seller seller = importerToDelete.get().getSeller();
            if (seller != null){
                seller.setImporter(null);
                sellerJpaRepository.save(seller);
            }
            importerJpaRepository.deleteById(importerId);
        }else{
            throw new NotFoundException("Nie ma takiego importera");
        }
    }
    public Importer fullImporterUpdate(Integer importerId, Importer updateImporter){
        if (importerJpaRepository.existsById(importerId)) {
            updateImporter.setImporterId(importerId);
            return importerJpaRepository.save(updateImporter);
        } else {
            throw new NotFoundException("Nie ma takiego importera");
        }
    }
    public Optional<Importer> setSellerForImporter(Integer sellerId, Integer importerId) {
        if(importerJpaRepository.existsById(importerId) && sellerJpaRepository.existsById(sellerId)) {
            Importer importer = importerJpaRepository.findById(importerId).get();
            Seller seller = sellerJpaRepository.findById(sellerId).get();
            seller.setImporter(importer);
            return Optional.of(importerJpaRepository.save(importer));
        }
        return Optional.empty();
    }
    public List<Importer> findByImporterName(String importerName){
          return importerJpaRepository.findByImporterNameContaining(importerName);
    }
}
