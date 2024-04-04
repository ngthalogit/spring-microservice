package org.example.resourceserver.controller;

import org.example.resourceserver.exception.NotFoundDeviceException;
import org.example.resourceserver.model.DeviceRequest;
import org.example.resourceserver.model.DeviceResponse;
import org.example.resourceserver.service.DevicesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/devices")
public class DevicesController {
    private final DevicesService devicesService;

    public DevicesController(DevicesService devicesService) {
        this.devicesService = devicesService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDevice(@PathVariable("id") int id) throws NotFoundDeviceException {
        return ResponseEntity.ok(devicesService.fetchOne(id));
    }

    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getAllDevices() {
        return ResponseEntity.ok(devicesService.fetchAll());
    }

    @PostMapping
    public ResponseEntity<String> createDevice(@RequestBody DeviceRequest deviceRequest) {
        int id = devicesService.create(deviceRequest);
        return new ResponseEntity<>("Device created, id: " + id, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDevice(@PathVariable("id") int id, @RequestBody DeviceRequest deviceRequest) throws NotFoundDeviceException {
        devicesService.update(id, deviceRequest);
        return ResponseEntity.ok("Device updated, id: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable("id") int id) throws NotFoundDeviceException {
        devicesService.delete(id);
        return ResponseEntity.ok("Device deleted, id: " + id);
    }
}
