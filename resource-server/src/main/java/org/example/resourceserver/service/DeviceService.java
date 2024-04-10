package org.example.resourceserver.service;

import org.example.resourceserver.exception.NotFoundDeviceException;
import org.example.resourceserver.model.DeviceRequest;
import org.example.resourceserver.model.DeviceResponse;

import java.util.List;

public interface DeviceService {
    List<DeviceResponse> fetchAll();

    DeviceResponse fetchOne(int id) throws NotFoundDeviceException;

    int create(DeviceRequest deviceRequest);

    void update(int id, DeviceRequest deviceRequest) throws NotFoundDeviceException;

    void delete(int id) throws NotFoundDeviceException;
}
