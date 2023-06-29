package com.warehause.warehause1.service;

import com.warehause.warehause1.exceptionHandler.NotFoundException;
import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.model.Device;
import com.warehause.warehause1.repository.DeviceJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceService {
    private final DeviceJpaRepository deviceRepository;

    public DeviceService(DeviceJpaRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }
    public Iterable<Device> getAllDevices(){
        return deviceRepository.findAll();
    }
    public Device getDeviceById(int id){
        Optional<Device> optionalDevice = deviceRepository.findById(id);
        if (optionalDevice.isEmpty()) {
            throw new NotFoundException("Nie ma takiego urządzenia");
        }
        return optionalDevice.get();
    }

    public Optional <Device> saveNew(Device device) {
        if (device.getDeviceId() != null && deviceRepository.existsById(device.getDeviceId())) {
            return Optional.empty();
        } else {
            return Optional.of(deviceRepository.save(device));
        }
    }
    public void removeById(int id){
        deviceRepository.deleteById(id);
    }

    public Optional<Device> fulDeviceUpdate(Integer deviceId, Device updateDevice){
        if(deviceRepository.existsById(deviceId)){
            updateDevice.setDeviceId(deviceId);
            return Optional.of(deviceRepository.save(updateDevice));
        }else{
            return Optional.empty();
        }
    }
}
