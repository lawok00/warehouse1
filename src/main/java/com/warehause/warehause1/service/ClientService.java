package com.warehause.warehause1.service;

import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.repository.ClientJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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

}
