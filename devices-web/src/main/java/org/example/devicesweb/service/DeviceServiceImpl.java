package org.example.devicesweb.service;

import org.example.devicesweb.model.Device;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Override
    public List<Device> getAllDevices() {

        return List.of();
    }
}
