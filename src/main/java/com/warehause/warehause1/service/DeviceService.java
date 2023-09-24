package com.warehause.warehause1.service;

import com.warehause.warehause1.exceptionHandler.NotFoundException;
import com.warehause.warehause1.model.Device;
import com.warehause.warehause1.repository.DeviceJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceService {
    private final DeviceJpaRepository deviceJpaRepository;

    public DeviceService(DeviceJpaRepository deviceRepository) {
        this.deviceJpaRepository = deviceRepository;
    }
    public Iterable<Device> getAllDevices(){
        return deviceJpaRepository.findAll();
    }
    public Device getDeviceById(int id){
        Optional<Device> optionalDevice = deviceJpaRepository.findById(id);
        if (optionalDevice.isEmpty()) {
            throw new NotFoundException("Nie ma takiego urządzenia");
        }
        return optionalDevice.get();
    }

    public Device saveNew(Device device) {
        if (device.getDeviceId() != null && deviceJpaRepository.existsById(device.getDeviceId())) {
            throw new NotFoundException("Urządzenie o podanym Id już istnieje");
        } else {
            return deviceJpaRepository.save(device);
        }
    }
    public void deleteDeviceById(int deviceId){
        Optional<Device> deviceToDelete = deviceJpaRepository.findById(deviceId);
        if (deviceToDelete.isPresent()) {
            deviceJpaRepository.deleteById(deviceId);
        } else {
            throw new NotFoundException("Nie ma takiego urządzenia");
        }
    }

    public Device fullDeviceUpdate(Integer deviceId, Device updateDevice){
        if (deviceJpaRepository.existsById(deviceId)) {
            updateDevice.setDeviceId(deviceId);
            return deviceJpaRepository.save(updateDevice);
        } else {
            throw new NotFoundException("Nie ma takiego urządzenia");
        }
    }
}
