package com.warehause.warehause1.service;

import com.warehause.warehause1.exceptionHandler.NotFoundException;
import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.model.Device;
import com.warehause.warehause1.repository.ClientJpaRepository;
import com.warehause.warehause1.repository.SellerJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.OpenOption;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientJpaRepository clientJpaRepository;
    private final SellerJpaRepository sellerJpaRepository;
    private final DeviceService deviceService;

    public ClientService(ClientJpaRepository clientJpaRepository, SellerJpaRepository sellerJpaRepository, DeviceService deviceService) {
        this.clientJpaRepository = clientJpaRepository;
        this.sellerJpaRepository = sellerJpaRepository;
        this.deviceService = deviceService;
    }


    public Iterable<Client> getAllClients() {
        return clientJpaRepository.findAll();
    }

    public Client getClientById(int id) {
        Optional<Client> optionalClient = clientJpaRepository.findById(id);
        if (optionalClient.isEmpty()) {
            throw new NotFoundException("Nie ma takiego klienta");
        }
        return optionalClient.get();
    }

    public Client saveNew(Client client) {
        if (client.getClientId() != null && clientJpaRepository.existsById(client.getClientId())) {
            throw new NotFoundException("Klient o podanym Id już istnieje");
        } else {
            return clientJpaRepository.save(client);
        }
    }

    public void deleteClientById(int clientId) {
        Optional<Client> clientToDelete = clientJpaRepository.findById(clientId);
        if (clientToDelete.isPresent()) {
            clientJpaRepository.deleteById(clientId);
        } else {
            throw new NotFoundException("Nie ma takiego klienta");
        }
    }

    public Client fullClientUpdate(Integer clientId, Client updateClient) {
        if (clientJpaRepository.existsById(clientId)) {
            updateClient.setClientId(clientId);
            return clientJpaRepository.save(updateClient);
        } else {
            throw new NotFoundException("Nie ma takiego klienta");
        }
    }
    public void addDeviceToClient(Integer clientId, Integer deviceId) {
        Client client = getClientById(clientId);
        Device device1 = deviceService.getDeviceById(deviceId);
        List<Device> deviceList = client.getDeviceList();
        deviceList.add(device1);
        client.setDeviceList(deviceList);
        clientJpaRepository.save(client);
    }

}
