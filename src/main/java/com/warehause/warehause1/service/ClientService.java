package com.warehause.warehause1.service;

import com.warehause.warehause1.exceptionHandler.NotFoundException;
import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.model.Device;
import com.warehause.warehause1.model.Seller;
import com.warehause.warehause1.repository.ClientJpaRepository;
import com.warehause.warehause1.repository.SellerJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Client getClientById(int id){
        Optional<Client> optionalClient = clientJpaRepository.findById(id);
        if (optionalClient.isEmpty()){
            throw new NotFoundException("Nie ma takiego klienta");
        }
        return optionalClient.get();
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
           clientJpaRepository.deleteById(clientId);
      }else{
            throw new NotFoundException("Nie ma takiego klienta");
        }
    }



    public Optional<Client> fulClientUpdate(Integer clientId, Client updateClient){
        if(clientJpaRepository.existsById(clientId)){
            updateClient.setClientId(clientId);
            return Optional.of(clientJpaRepository.save(updateClient));
            }else{
            return Optional.empty();
        }
    }
    public void addDeviceToClient(Client client, Device device){
        List<Device> deviceList = client.getDeviceList();
        deviceList.add(device);
        client.setDeviceList(deviceList);
        clientJpaRepository.save(client);
    }

}
