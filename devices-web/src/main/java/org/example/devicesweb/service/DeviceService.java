package org.example.devicesweb.service;

import org.example.devicesweb.model.Device;
import org.example.devicesweb.model.DeviceResponse;

import java.util.List;

public interface DeviceService {
    List<DeviceResponse> getAllDevices();
}
