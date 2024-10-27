package com.chotchip.Project3.services;

import com.chotchip.Project3.dto.SensorDTO;
import com.chotchip.Project3.exception.SensorNotFoundException;
import com.chotchip.Project3.mapper.SensorMapper;
import com.chotchip.Project3.models.Sensor;
import com.chotchip.Project3.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SensorsService {
    private final SensorsRepository sensorsRepository;
    private final SensorMapper sensorMapper;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository, SensorMapper sensorMapper) {
        this.sensorsRepository = sensorsRepository;
        this.sensorMapper = sensorMapper;
    }

    @Transactional
    public void save(SensorDTO sensorDTO) {
        var entity = sensorMapper.toEntity(sensorDTO);
        sensorsRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public SensorDTO findById(int id) {
        var sensor = sensorsRepository.findById(id).orElseThrow(SensorNotFoundException::new);
        return sensorMapper.toDTO(sensor);
    }
    @Transactional(readOnly = true)
    public Sensor findByName(String name) {
        return sensorsRepository.findByName(name).orElseThrow(SensorNotFoundException::new);
    }


}
