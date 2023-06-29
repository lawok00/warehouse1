package com.warehause.warehause1.controller;

import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.model.Device;
import com.warehause.warehause1.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/device")
public class DeviceController {
    private DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }
    @GetMapping
    public Iterable<Device> getAllDevices(){
        return deviceService.getAllDevices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable int id){
        Device response = deviceService.getDeviceById(id);
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<Device> postDevice(@RequestBody Device requestDevice) {
        Optional <Device> savedDevice = deviceService.saveNew(requestDevice);
        if (savedDevice.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedDevice.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable int id){
        deviceService.removeById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/fullUpdate/{id}")
    public ResponseEntity<String> fullUpdateDevice(@PathVariable Integer id, @RequestBody  Device updateDevice){
        Optional<Device> deviceBeUpdated = deviceService.fulDeviceUpdate(id, updateDevice);
        if(deviceBeUpdated.isPresent()){
            return ResponseEntity.ok("Client updated.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
