package org.example.resourceserver.controller;

import org.example.resourceserver.exception.NotFoundDeviceException;
import org.example.resourceserver.model.DeviceRequest;
import org.example.resourceserver.model.DeviceResponse;
import org.example.resourceserver.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/devices")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDevice(@PathVariable("id") int id) throws NotFoundDeviceException {
        return ResponseEntity.ok(deviceService.fetchOne(id));
    }

    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getAllDevices() {
        return ResponseEntity.ok(deviceService.fetchAll());
    }

    @PostMapping
    public ResponseEntity<String> createDevice(@RequestBody DeviceRequest deviceRequest) {
        int id = deviceService.create(deviceRequest);
        return new ResponseEntity<>("Device created, id: " + id, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDevice(@PathVariable("id") int id, @RequestBody DeviceRequest deviceRequest) throws NotFoundDeviceException {
        deviceService.update(id, deviceRequest);
        return ResponseEntity.ok("Device updated, id: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable("id") int id) throws NotFoundDeviceException {
        deviceService.delete(id);
        return ResponseEntity.ok("Device deleted, id: " + id);
    }
}
