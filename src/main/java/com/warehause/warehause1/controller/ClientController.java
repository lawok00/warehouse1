package com.warehause.warehause1.controller;

import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public ResponseEntity<Client> postClient(@RequestBody Client requestClient) {
        Optional <Client> savedClient = clientService.saveNew(requestClient);
        if (savedClient.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedClient.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable int id){
        if(clientService.getClientById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            clientService.deleteClientById(id);
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/fullUpdate/{id}")
    public ResponseEntity<String> fullUpdateClient(@PathVariable Integer id, @RequestBody Client updateClient){
    Optional<Client> clientBeUpdated = clientService.fulClientUpdate(id, updateClient);
    if(clientBeUpdated.isPresent()){
        return ResponseEntity.ok("Client updated.");
    }else{
        return ResponseEntity.notFound().build();
    }
    }


}
