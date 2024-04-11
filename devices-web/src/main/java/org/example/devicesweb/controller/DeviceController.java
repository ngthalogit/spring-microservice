package org.example.devicesweb.controller;

import org.example.devicesweb.model.Device;
import org.example.devicesweb.model.DeviceResponse;
import org.example.devicesweb.service.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/devices")
    public String devices(Model model) {
        List<DeviceResponse> devices = deviceService.getAllDevices();
        model.addAttribute("devices", devices);
        return "devices";
    }
}
