package com.chotchip.Project3.services;

import com.chotchip.Project3.models.Measurement;
import com.chotchip.Project3.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository) {
        this.measurementsRepository = measurementsRepository;
    }

    @Transactional
    public void save(Measurement measurement) {

        measurement.setTimeStamp(LocalDateTime.now());
        measurementsRepository.save(measurement);
    }

    @Transactional(readOnly = true)
    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Measurement> findAllRainingDays() {
        return measurementsRepository.findByRainingTrue();
    }

}
