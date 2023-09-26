package com.warehause.warehause1.controller;

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
        Seller response = sellerService.getSellerById(id);
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<Seller> postImporter(@RequestBody Seller requestSeller) {
        Seller saveSeller = sellerService.saveNew(requestSeller);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable int id){
       sellerService.deleteSellerById(id);
       return ResponseEntity.noContent().build();
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
    @PatchMapping("/clientForSeller/{sellerId}/setClient/{clientId}")
    public ResponseEntity<String> setClientForSeller(@PathVariable Integer sellerId, @PathVariable Integer clientId) { //zmienna ze ścieżki, linijka 70
        Optional<Seller> clientForSeller = sellerService.setSellerForClient(clientId, sellerId);
        if(clientForSeller.isPresent()) {
            return ResponseEntity.ok("Client set for seller succesfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
