package org.example.resourceserver.service;

import org.example.resourceserver.exception.NotFoundDeviceException;
import org.example.resourceserver.model.Device;
import org.example.resourceserver.model.DeviceRequest;
import org.example.resourceserver.model.DeviceResponse;
import org.example.resourceserver.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<DeviceResponse> fetchAll() {
        return deviceRepository.findAll().stream().map(DeviceResponse::new).toList();
    }

    @Override
    public DeviceResponse fetchOne(int id) throws NotFoundDeviceException {
        if (deviceRepository.existsById(id)) {
            return new DeviceResponse(deviceRepository.findById(id));
        }
        throw new NotFoundDeviceException(String.valueOf(id));
    }

    @Override
    public int create(DeviceRequest deviceRequest) {
        int id = new Random().nextInt();
        while (deviceRepository.existsById(id)) {
            id = new Random().nextInt();
        }
        deviceRepository.save(Device.fromDeviceRequest(id, deviceRequest));
        return id;
    }

    @Override
    public void update(int id, DeviceRequest deviceRequest) throws NotFoundDeviceException {
        if (deviceRepository.existsById(id)) {
            deviceRepository.save(Device.fromDeviceRequest(id, deviceRequest));
        }
        throw new NotFoundDeviceException(String.valueOf(id));
    }

    @Override
    public void delete(int id) throws NotFoundDeviceException {
        if (deviceRepository.existsById(id)) {
            deviceRepository.deleteById(id);
        }
        throw new NotFoundDeviceException(String.valueOf(id));
    }
}
