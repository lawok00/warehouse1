package com.warehause.warehause1.controller;

import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public Iterable<Client> getAllClients(){
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable int id){
        Optional<Client> response = clientService.getClientById(id);
        return response.
                map(clientResponse -> ResponseEntity.ok(clientResponse))
                .orElseGet(() ->ResponseEntity.notFound().build())                ;
    }
}
