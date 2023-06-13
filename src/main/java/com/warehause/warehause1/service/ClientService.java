package com.warehause.warehause1.service;

import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.repository.ClientJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService{
    private final ClientJpaRepository repository;

    public ClientService(ClientJpaRepository repository) {
        this.repository = repository;
    }
    public Iterable<Client> getAllClients(){
        return repository.findAll();
    }
}
