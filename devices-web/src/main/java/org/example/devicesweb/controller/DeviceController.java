package org.example.devicesweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeviceController {

    @GetMapping("/devices")
    public String devices(Model model) {

        return "devices";
    }
}
