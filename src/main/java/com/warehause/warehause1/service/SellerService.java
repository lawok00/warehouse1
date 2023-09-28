package com.warehause.warehause1.service;

import com.warehause.warehause1.exceptionHandler.NotFoundException;
import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.model.Seller;
import com.warehause.warehause1.repository.ClientJpaRepository;
import com.warehause.warehause1.repository.SellerJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerService {
    private final SellerJpaRepository sellerJpaRepository;
    private final ClientJpaRepository clientJpaRepository;
    private final Client client;


    public SellerService(SellerJpaRepository sellerJpaRepository, ClientJpaRepository clientJpaRepository, Client client) {
        this.sellerJpaRepository = sellerJpaRepository;
        this.clientJpaRepository = clientJpaRepository;
        this.client = client;
    }
    
    public Seller getSellerById(int id) {
        Optional<Seller> optionalSeller = sellerJpaRepository.findById(id);
        if (optionalSeller.isEmpty()) {
            throw new NotFoundException("Nie ma takiego sprzedawcy");
        }
        return optionalSeller.get();
    }
    public Iterable<Seller> getAllSellers(){
        return sellerJpaRepository.findAll();
    }

    public Seller saveNew(Seller seller) {
        if (seller.getSellerId() != null && sellerJpaRepository.existsById(seller.getSellerId())) {
            throw new NotFoundException("Sprzedawca o podanym Id już istnieje");
        } else {
            return sellerJpaRepository.save(seller);
        }
    }

        public void deleteSellerById(int id){
        Optional<Seller> sellerToDelete = sellerJpaRepository.findById(id);
        if (sellerToDelete.isPresent()) {            
            for (Client client : sellerToDelete.get().getClients()) {
                client.setSeller(null);
                clientJpaRepository.save(client);
            }
            sellerJpaRepository.deleteById(id);
        }
    }
    public Seller fullSellerUpdate(Integer sellerId, Seller updateSeller){
        if (sellerJpaRepository.existsById(sellerId)) {
            updateSeller.setSellerId(sellerId);
            return sellerJpaRepository.save(updateSeller);
        } else {
            throw new NotFoundException("Nie ma takiego sprzedawcy");
        }
    }
    public Optional<Seller> setSellerForClient(Integer clientId, Integer sellerId) {
        if(sellerJpaRepository.existsById(sellerId) && clientJpaRepository.existsById(clientId)) {
            Client client = clientJpaRepository.findById(clientId).get();
            Seller seller = sellerJpaRepository.findById(sellerId).get();
            client.setSeller(seller);
            return Optional.of(sellerJpaRepository.save(seller));
        }
        return Optional.empty();
    }
}
