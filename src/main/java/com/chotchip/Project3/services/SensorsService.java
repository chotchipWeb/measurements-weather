package com.chotchip.Project3.services;

import com.chotchip.Project3.exception.SensorNotFoundException;
import com.chotchip.Project3.models.Sensor;
import com.chotchip.Project3.repositories.SensorsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SensorsService {
    private final SensorsRepository sensorsRepository;

    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;

    }

    @Transactional
    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }

    @Transactional(readOnly = true)
    public Sensor findById(int id) {
        return sensorsRepository.findById(id).orElseThrow(SensorNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Sensor findByName(String name) {
        return sensorsRepository.findByName(name).orElseThrow(SensorNotFoundException::new);
    }


}
