package org.example.resourceserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DeviceRequest {
    private String name;
    private String description;
    private String price;
}
