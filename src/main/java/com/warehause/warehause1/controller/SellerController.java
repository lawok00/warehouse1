package com.warehause.warehause1.controller;

import com.warehause.warehause1.model.Importer;
import com.warehause.warehause1.model.Seller;
import com.warehause.warehause1.service.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/seller")
public class SellerController {
    private SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }
    @GetMapping
    public Iterable<Seller> getAllSellers(){
        return sellerService.getAllSellers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerId(@PathVariable int id){
        Optional<Seller> response = sellerService.getSellerById(id);
        return response.
                map(sellerResponse -> ResponseEntity.ok(sellerResponse))
                .orElseGet(() ->ResponseEntity.notFound().build())                ;
    }
    @PostMapping
    public ResponseEntity<Seller> postImporter(@RequestBody Seller requestSeller) {
        Optional <Seller> savedSeller = sellerService.saveNew(requestSeller);
        if (savedSeller.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedSeller.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable int id){
        if(sellerService.getSellerById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            sellerService.removeById(id);
            return ResponseEntity.noContent().build();
        }
    }
    @PutMapping("/fullUpdate/{id}")
    public ResponseEntity<String> fullUpdateSeller(@PathVariable Integer id, @RequestBody  Seller updateSeller){
        Optional<Seller> sellerBeUpdated = sellerService.fulSellerUpdate(id, updateSeller);
        if(sellerBeUpdated.isPresent()){
            return ResponseEntity.ok("Client updated.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
