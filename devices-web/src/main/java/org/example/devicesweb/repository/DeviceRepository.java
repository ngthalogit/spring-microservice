package org.example.devicesweb.repository;

import org.example.devicesweb.model.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {
    List<Device> findAll();
}
