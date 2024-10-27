package com.chotchip.Project3.services;

import com.chotchip.Project3.dto.MeasurementDTO;
import com.chotchip.Project3.exception.measurement.MeasurementNotFoundException;
import com.chotchip.Project3.mapper.MeasurementsListMapper;
import com.chotchip.Project3.mapper.MeasurementsMapper;
import com.chotchip.Project3.models.Measurement;
import com.chotchip.Project3.repositories.MeasurementsRepository;
import jakarta.el.MethodNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeasurementsService {
    private final SensorsService sensorsService;
    private final MeasurementsRepository measurementsRepository;

    private final MeasurementsMapper measurementsMapper;
    private final MeasurementsListMapper measurementsListMapper;

    @Autowired
    public MeasurementsService(SensorsService sensorsService, MeasurementsRepository measurementsRepository, MeasurementsMapper measurementsMapper, MeasurementsListMapper measurementsListMapper) {
        this.sensorsService = sensorsService;
        this.measurementsRepository = measurementsRepository;
        this.measurementsMapper = measurementsMapper;
        this.measurementsListMapper = measurementsListMapper;
    }

    @Transactional
    public void save(MeasurementDTO measurementDTO) {
        var entity = measurementsMapper.toEntity(measurementDTO);
        if (sensorsService.findByName(entity.getSensor().getName()) == null) return;
        entity.setTimeStamp(LocalDateTime.now());
        measurementsRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<MeasurementDTO> findAll() {
        List<Measurement> listEntity = measurementsRepository.findAll();
        return measurementsListMapper.toDTOList(listEntity);
    }

    @Transactional(readOnly = true)
    public List<MeasurementDTO> findAllRainingDays() {
        List<Measurement> byRainingTrueList = measurementsRepository.findByRainingTrue();
        return measurementsListMapper.toDTOList(byRainingTrueList);
    }

    @Transactional(readOnly = true)
    public MeasurementDTO findById(int id) {
        var entity = measurementsRepository.findById(id).orElseThrow(MeasurementNotFoundException::new);
        return measurementsMapper.toDTO(entity);
    }

}
