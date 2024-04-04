package org.example.resourceserver.service;

import org.example.resourceserver.exception.ExistingDeviceException;
import org.example.resourceserver.exception.NotFoundDeviceException;
import org.example.resourceserver.model.Device;
import org.example.resourceserver.model.DeviceRequest;
import org.example.resourceserver.model.DeviceResponse;
import org.example.resourceserver.repository.DevicesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class DeviceServiceImpl implements DevicesService {
    private final DevicesRepository devicesRepository;

    public DeviceServiceImpl(DevicesRepository devicesRepository) {
        this.devicesRepository = devicesRepository;
    }

    @Override
    public List<DeviceResponse> fetchAll() {
        return devicesRepository.findAll().stream().map(DeviceResponse::new).toList();
    }

    @Override
    public DeviceResponse fetchOne(int id) throws NotFoundDeviceException {
        if (devicesRepository.existsById(id)) {
            return new DeviceResponse(devicesRepository.findById(id));
        }
        throw new NotFoundDeviceException(String.valueOf(id));
    }

    @Override
    public int create(DeviceRequest deviceRequest) {
        int id = new Random().nextInt();
        while (devicesRepository.existsById(id)) {
            id = new Random().nextInt();
        }
        devicesRepository.save(Device.fromDeviceRequest(id, deviceRequest));
        return id;
    }

    @Override
    public void update(int id, DeviceRequest deviceRequest) throws NotFoundDeviceException {
        if (devicesRepository.existsById(id)) {
            devicesRepository.save(Device.fromDeviceRequest(id, deviceRequest));
        }
        throw new NotFoundDeviceException(String.valueOf(id));
    }

    @Override
    public void delete(int id) throws NotFoundDeviceException {
        if (devicesRepository.existsById(id)) {
            devicesRepository.deleteById(id);
        }
        throw new NotFoundDeviceException(String.valueOf(id));
    }
}
