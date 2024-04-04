package org.example.resourceserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "devices")
public class Device {
    @Id
    private int id;
    private String name;
    private String description;
    private String price;

    public static Device fromDeviceRequest(int id, DeviceRequest request) {
        return new Device(id, request.getName(), request.getDescription(), request.getPrice());
    }
}
