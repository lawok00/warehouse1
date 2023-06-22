package com.warehause.warehause1.service;

import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.model.Seller;
import com.warehause.warehause1.repository.ClientJpaRepository;
import com.warehause.warehause1.repository.SellerJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService{
    private final ClientJpaRepository clientJpaRepository;
    private final SellerJpaRepository sellerJpaRepository;


    public ClientService(ClientJpaRepository repository, SellerJpaRepository sellerJpaRepository) {
        this.clientJpaRepository = repository;
        this.sellerJpaRepository = sellerJpaRepository;
    }
    public Iterable<Client> getAllClients(){
        return clientJpaRepository.findAll();
    }
    public Optional<Client> getClientById(int id){
        return clientJpaRepository.findById(id);
    }
    public Optional <Client> saveNew(Client client) {
        if (client.getClientId() != null && clientJpaRepository.existsById(client.getClientId())) {
            return Optional.empty();
        } else {
            return Optional.of(clientJpaRepository.save(client));
        }
    }
    public void deleteClientById(int clientId) {
        Optional<Client> clientToDelete = clientJpaRepository.findById(clientId);
        if(clientToDelete.isPresent()) {
            Seller seller = clientToDelete.get().getSeller();
            if(seller != null) {
                seller.setClients(null); //utrata powiązania
                sellerJpaRepository.save(seller);
            }
            clientJpaRepository.deleteById(clientId);
        }
    }

//    public void removeById(int id){
//        repository.deleteById(id);
//    }

    public Optional<Client> fulClientUpdate(Integer clientId, Client updateClient){
        if(clientJpaRepository.existsById(clientId)){
            updateClient.setClientId(clientId);
            return Optional.of(clientJpaRepository.save(updateClient));
            }else{
            return Optional.empty();
        }
    }

}
