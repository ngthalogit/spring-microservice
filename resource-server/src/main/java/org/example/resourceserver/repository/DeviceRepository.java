package org.example.resourceserver.repository;

import org.example.resourceserver.model.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {
    List<Device> findAll();
    Device findById(int id);
}
