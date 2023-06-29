package com.warehause.warehause1.controller;

import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.model.Device;
import com.warehause.warehause1.service.ClientService;
import com.warehause.warehause1.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;
    private DeviceService deviceService;

    public DeviceService getDeviceService() {
        return deviceService;
    }

    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public ClientController(ClientService clientService, DeviceService deviceService) {
        this.clientService = clientService;
        this.deviceService = deviceService;
    }

    @GetMapping
    public Iterable<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable int id) {
        Client response = clientService.getClientById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Client> postClient(@RequestBody Client requestClient) {
        Client savedClient = clientService.saveNew(requestClient);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable int id) {
            clientService.deleteClientById(id);
            return ResponseEntity.noContent().build();
    }

    @PutMapping("/fullUpdate/{id}")
    public ResponseEntity<String> fullUpdateClient(@PathVariable Integer id, @RequestBody Client updateClient) {
        Client clientBeUpdated = clientService.fullClientUpdate(id, updateClient);
            return ResponseEntity.status(HttpStatus.UPGRADE_REQUIRED).build();
    }

    @PatchMapping("{id}/addDeviceToClient/{deviceId}")
    public ResponseEntity<Void> addDeviceToClient(@PathVariable int id, @PathVariable int deviceId){

        clientService.addDeviceToClient(id, deviceId);
        return ResponseEntity.noContent().build();
    }


}
