package com.warehause.warehause1.service;

import com.warehause.warehause1.exceptionHandler.NotFoundException;
import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.model.Device;
import com.warehause.warehause1.repository.ClientJpaRepository;
import com.warehause.warehause1.repository.DeviceJpaRepository;
import com.warehause.warehause1.repository.SellerJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {
    @Mock
    private ClientJpaRepository clientJpaRepository;
    @Mock
    private SellerJpaRepository sellerJpaRepository;
    @Mock
    private DeviceJpaRepository deviceJpaRepository;
    @Mock
    private DeviceService deviceService;
    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllClientsShouldReturnListOfAllClients() {
        Client client1 = new Client(1, "Adam Adamski", 5);
        Client client2 = new Client(2, "Bogdan Bogdański", 6);
        Client client3 = new Client(3, "Cezary Czarecki", 2);
        List<Client> checkClients = List.of(client1, client2, client3);
        when(clientJpaRepository.findAll()).thenReturn(checkClients);
        List<Client> myClients = (List<Client>) clientService.getAllClients();
        assertEquals(checkClients, myClients);
    }

    @Test
    void getClientByIdShouldReturnClientById() {
        Client client = new Client(1, "Adam Adamski", 5);
        Mockito.when(clientJpaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(client));
        Optional<Client> retrievedClient = Optional.ofNullable(clientService.getClientById(1));
        Assertions.assertTrue(retrievedClient.isPresent());
        assertEquals(client, retrievedClient.get());
    }

    @Test
    void getClientByIdShouldReturnNullWhenClientDoesntExist() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            int clientToFindById = 1000;
            when(clientJpaRepository.findById(clientToFindById)).thenReturn(Optional.empty());
            clientService.getClientById(clientToFindById);
        });
        Assertions.assertEquals("Nie ma takiego klienta", thrown.getMessage());
    }

    @Test
    void saveNewShouldAddAndReturnNewClient() {
        Client client = new Client(1, "Adam Adamski", 5);
        Mockito.when(clientJpaRepository.save(client)).thenReturn(client);
        Client saveClient = this.clientService.saveNew(client);
        assertEquals(saveClient, client);
    }

    @Test
    void saveNewShouldNotSaveExistingClient() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Client client = new Client(1, "Adam Adamski", 5);
            when(clientJpaRepository.existsById(client.getClientId())).thenReturn(true);
            this.clientService.saveNew(client);
        });
        assertEquals("Klient o podanym Id już istnieje", thrown.getMessage());
    }

    @Test
    void deleteClientByIdShouldDeleteClient() {
        Integer clientId = 1;
        Client client = new Client(clientId, "Adam Adamski", 5);
        Mockito.when(clientJpaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(client));
        clientService.deleteClientById(clientId);
        verify(clientJpaRepository, times(1)).deleteById(clientId);
    }

    @Test
    void deleteClientByIdShouldNotDeleteClient() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Integer clientIdToDelete = 300;
            Mockito.when(clientJpaRepository.existsById(clientIdToDelete)).thenReturn(false);
            clientService.deleteClientById(clientIdToDelete);
        });
        assertEquals("Nie ma takiego klienta", thrown.getMessage());
    }

    @Test
    void fullClientUpdateShouldUpdateClient() {
        Integer clientId = 1;
        Client existingClient = new Client(clientId, "Adam Adamski", 5);
        existingClient.setClientId(clientId);
        Client updatedClient = new Client();
        updatedClient.setClientId(clientId);
        updatedClient.setClientName("Updated");
        updatedClient.setClientNote(4);
        when(clientJpaRepository.existsById(clientId)).thenReturn(true);
        when(clientJpaRepository.save(updatedClient)).thenReturn(updatedClient);
        Client result = clientService.fullClientUpdate(clientId, updatedClient);
        verify(clientJpaRepository, times(1)).save(updatedClient);
    }

    @Test
    void fullClientUpdateShouldNotUpdateClientWhenNotExist() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Integer clientId = 1;
            Client updatedClient = new Client();
            updatedClient.setClientId(300);
            when(clientJpaRepository.existsById(clientId)).thenReturn(false);
            Client result = clientService.fullClientUpdate(clientId, updatedClient);
        });
        assertEquals("Nie ma takiego klienta", thrown.getMessage());
    }

    @Test
    void addDeviceToClient() {
        Integer clientId = 1;
        Integer deviceId = 1;
        Client client = new Client(clientId, "Adam Adamski", 5);
        client.setDeviceList(new ArrayList<>());
        Device device = new Device(deviceId, "Daewoo", 100f, "Electronic");
        Mockito.when(clientJpaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(client));
        Mockito.when(deviceService.getDeviceById(Mockito.anyInt())).thenReturn(device);
        clientService.addDeviceToClient(clientId, deviceId);
        client.setDeviceList(List.of(device));
        verify(clientJpaRepository, times(1)).save(client);
    }

    @Test
    void addDeviceToClientShouldNotAddWithClientNotExist() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Integer clientId = 300;
            Client client = new Client();
            when(clientJpaRepository.existsById(clientId)).thenReturn(false);
            clientService.getClientById(clientId);
        });
        assertEquals("Nie ma takiego klienta", thrown.getMessage());
    }

}