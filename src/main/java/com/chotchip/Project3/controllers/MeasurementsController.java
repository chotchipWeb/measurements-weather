package com.chotchip.Project3.controllers;

import com.chotchip.Project3.dto.MeasurementDTO;
import com.chotchip.Project3.dto.SensorDTO;
import com.chotchip.Project3.exception.SensorNotFoundException;
import com.chotchip.Project3.exception.measurement.MeasurementNotCreatedException;
import com.chotchip.Project3.exception.response.MeasurementErrorResponse;
import com.chotchip.Project3.models.Measurement;
import com.chotchip.Project3.services.MeasurementsService;
import com.chotchip.Project3.services.SensorsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/measurements")
@Slf4j
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper, SensorsService sensorsService) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.sensorsService = sensorsService;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> save(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError error : fieldErrors) {
                stringBuilder
                        .append(error.getField())
                        .append("-")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            log.error(stringBuilder.toString());
            throw new MeasurementNotCreatedException(stringBuilder.toString());
        }
        Measurement measurement = convertToMeasurement(measurementDTO);
        measurement.setSensor(sensorsService.findByName(measurementDTO.getSensor().getName()));
        measurementsService.save(measurement);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping()
    public List<MeasurementDTO> showAll() {
        log.info("Take from db");
        return measurementsService.findAll().stream().map(this::convertToMeasurementDTO).toList();
    }

    @GetMapping("/rainyDaysCount")
    public List<MeasurementDTO> showAllRainDays() {
        return measurementsService.findAllRainingDays().stream().map(this::convertToMeasurementDTO).toList();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handlerException(MeasurementNotCreatedException e) {
        return new ResponseEntity<>(new MeasurementErrorResponse(e.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handlerException(SensorNotFoundException e) {
        return new ResponseEntity<>(new MeasurementErrorResponse("Sensor not found!", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
