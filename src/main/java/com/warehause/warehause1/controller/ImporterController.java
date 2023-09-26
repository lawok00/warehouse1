package com.warehause.warehause1.controller;

import com.warehause.warehause1.model.Importer;
import com.warehause.warehause1.service.ImporterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/importer")
public class ImporterController {
    private ImporterService importerService;

    public ImporterController(ImporterService importerService) {
        this.importerService = importerService;
    }
    @GetMapping
    public Iterable<Importer> getAllImporters(){
        return importerService.getAllImporters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Importer> getImporterId(@PathVariable int id){
        Importer response = importerService.getImporterById(id);
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<Importer> postImporter(@RequestBody Importer requestImporter) {
        Importer saveImporter = importerService.saveNew(requestImporter);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImporter(@PathVariable int id){
       importerService.deleteImporterById(id);
       return ResponseEntity.noContent().build();
    }
    @PutMapping("/fullUpdate/{id}")
    public ResponseEntity<String> fullUpdateImporter(@PathVariable Integer id, @RequestBody  Importer updateImporter){
       Importer importerBeUpdated = importerService.fullImporterUpdate(id, updateImporter);
       return ResponseEntity.status(HttpStatus.UPGRADE_REQUIRED).build();
    }

    @PatchMapping("/sellerForImporter/{importerId}/setSeller/{sellerId}")
    public ResponseEntity<String> setSellerForImporter(@PathVariable Integer importerId, @PathVariable Integer sellerId) { //zmienna ze ścieżki, linijka 70
        Optional<Importer> sellerForImporter = importerService.setSellerForImporter(sellerId, importerId);
        if(sellerForImporter.isPresent()) {
            return ResponseEntity.ok("Seller set for importer succesfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getImporterByName/{importerName}")
    public List <Importer> findImporterByName(@PathVariable String importerName){
        return importerService.findByImporterName(importerName);
    }
}
