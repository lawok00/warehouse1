package com.warehause.warehause1.service;

import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.repository.ClientJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService{
    private final ClientJpaRepository repository;

    public ClientService(ClientJpaRepository repository) {
        this.repository = repository;
    }
    public Iterable<Client> getAllClients(){
        return repository.findAll();
    }
    public Optional<Client> getClientById(int id){
        return repository.findById(id);
    }
    public Optional <Client> saveNew(Client client) {
        if (client.getClientId() != null && repository.existsById(client.getClientId())) {
            return Optional.empty();
        } else {
            return Optional.of(repository.save(client));
        }
    }

    public void removeById(int id){
        repository.deleteById(id);
    }

    public Optional<Client> fulClientUpdate(Integer clientId, Client updateClient){
        if(repository.existsById(clientId)){
            updateClient.setClientId(clientId);
            return Optional.of(repository.save(updateClient));
            }else{
            return Optional.empty();
        }
    }

}
