package com.warehause.warehause1.service;

import com.warehause.warehause1.model.Importer;
import com.warehause.warehause1.model.Seller;
import com.warehause.warehause1.repository.SellerJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerService {
    private final SellerJpaRepository sellerJpaRepository;


    public SellerService(SellerJpaRepository sellerJpaRepository) {
        this.sellerJpaRepository = sellerJpaRepository;
    }
    public Iterable<Seller> getAllSellers(){
        return sellerJpaRepository.findAll();
    }
    public Optional<Seller> getSellerById(int id){
        return sellerJpaRepository.findById(id);
    }

    public Optional <Seller> saveNew(Seller seller) {
        if (seller.getSellerId() != null && sellerJpaRepository.existsById(seller.getSellerId())) {
            return Optional.empty();
        } else {
            return Optional.of(sellerJpaRepository.save(seller));
        }
    }
    public void removeById(int id){
        sellerJpaRepository.deleteById(id);
    }

    public Optional<Seller> fulSellerUpdate(Integer sellerId, Seller updateSeller){
        if(sellerJpaRepository.existsById(sellerId)){
            updateSeller.setSellerId(sellerId);
            return Optional.of(sellerJpaRepository.save(updateSeller));
        }else{
            return Optional.empty();
        }
    }
}
