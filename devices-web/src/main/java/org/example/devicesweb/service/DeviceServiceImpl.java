package org.example.devicesweb.service;

import org.example.devicesweb.model.Device;
import org.example.devicesweb.model.DeviceResponse;
import org.example.devicesweb.model.TokenResponse;
import org.example.devicesweb.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<DeviceResponse> getAllDevices() {
        return deviceRepository.findAll().stream().map(DeviceResponse::new).toList();
    }

}
