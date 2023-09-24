package com.warehause.warehause1.service;

import com.warehause.warehause1.exceptionHandler.NotFoundException;
import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.model.Device;
import com.warehause.warehause1.repository.DeviceJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceServiceTest {
    @Mock
    private DeviceJpaRepository deviceJpaRepository;
    @InjectMocks
    private DeviceService deviceService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDevicesShouldReturnListOfAllDevices() {
        Device device1 = new Device(1, "AAB", 3.5f, "Router");
        Device device2 = new Device(2, "CC-Link", 6f, "Switch");
        Device device3 = new Device(3, "Krone", 20.7f, "Panel");
        List<Device> checkDevices = List.of(device1, device2, device3);
        when(deviceJpaRepository.findAll()).thenReturn(checkDevices);
        List<Device> myDevices = (List<Device>) deviceService.getAllDevices();
        assertEquals(checkDevices, myDevices);
    }

    @Test
    void getDeviceByIdShouldReturnDeviceById () {
        Device device = new Device(1, "AAB", 3.5f, "Router");
        Mockito.when(deviceJpaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(device));
        Optional<Device> retrievedDevice = Optional.ofNullable(deviceService.getDeviceById(1));
        Assertions.assertTrue(retrievedDevice.isPresent());
        assertEquals(device, retrievedDevice.get());
    }
    @Test
    void getDeviceByIdShouldReturnNullWhenDeviceDoesntExist() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            int deviceToFindById = 1000;
            when(deviceJpaRepository.findById(deviceToFindById)).thenReturn(Optional.empty());
            deviceService.getDeviceById(deviceToFindById);
        });
        Assertions.assertEquals("Nie ma takiego urządzenia", thrown.getMessage());
    }

    @Test
    void saveNewShouldAddAndReturnNewDevice() {
        Device device = new Device(1, "AAB", 3.5f, "Router");
        Mockito.when(deviceJpaRepository.save(device)).thenReturn(device);
        Device saveDevice = this.deviceService.saveNew(device);
        assertEquals(saveDevice, device);
    }
    @Test
    void saveNewShouldNotSaveExistingDeviceClient() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Device device = new Device(1, "AAB", 3.5f, "Router");
            when(deviceJpaRepository.existsById(device.getDeviceId())).thenReturn(true);
            this.deviceService.saveNew(device);
        });
        assertEquals("Urządzenie o podanym Id już istnieje", thrown.getMessage());
    }
    @Test
    void deleteDeviceByIdShouldDeleteDevice() {
        Integer deviceId = 1;
        Device device = new Device(deviceId, "AAB", 3.5f, "Router");
        Mockito.when(deviceJpaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(device));
        deviceService.deleteDeviceById(deviceId);
        verify(deviceJpaRepository, times(1)).deleteById(deviceId);
    }
    @Test
    void deleteDeviceByIdShouldNotDeleteDevice() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Integer deviceIdToDelete = 300;
            Mockito.when(deviceJpaRepository.existsById(deviceIdToDelete)).thenReturn(false);
            deviceService.deleteDeviceById(deviceIdToDelete);
        });
        assertEquals("Nie ma takiego urządzenia", thrown.getMessage());
    }

    @Test
    void fulDeviceUpdateShouldUpdateDevice() {
        Integer deviceId = 1;
        Device existingDevice = new Device(deviceId, "AAB", 3.5f, "Router");
        existingDevice.setDeviceId(deviceId);
        Device updatedDevice = new Device();
        updatedDevice.setDeviceId(deviceId);
        updatedDevice.setDeviceProducer("Updated");
        updatedDevice.setDevicePrice(7f);
        updatedDevice.setDeviceCategory("New");
        when(deviceJpaRepository.existsById(deviceId)).thenReturn(true);
        when(deviceJpaRepository.save(updatedDevice)).thenReturn(updatedDevice);
        Device result = deviceService.fullDeviceUpdate(deviceId, updatedDevice);
        verify(deviceJpaRepository, times(1)).save(updatedDevice);
    }
    @Test
    void fullDeviceUpdateShouldNotUpdateDeviceWhenNotExist() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Integer deviceId = 1;
            Device updatedDevice = new Device();
            updatedDevice.setDeviceId(300);
            when(deviceJpaRepository.existsById(deviceId)).thenReturn(false);
            Device result = deviceService.fullDeviceUpdate(deviceId, updatedDevice);
        });
        assertEquals("Nie ma takiego urządzenia", thrown.getMessage());
    }
}