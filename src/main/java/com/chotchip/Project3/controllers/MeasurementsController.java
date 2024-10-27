package com.chotchip.Project3.controllers;

import com.chotchip.Project3.dto.MeasurementDTO;
import com.chotchip.Project3.exception.SensorNotFoundException;
import com.chotchip.Project3.exception.measurement.MeasurementNotCreatedException;
import com.chotchip.Project3.exception.measurement.MeasurementNotFoundException;
import com.chotchip.Project3.exception.response.MeasurementErrorResponse;
import com.chotchip.Project3.services.MeasurementsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService) {
        this.measurementsService = measurementsService;
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
        measurementsService.save(measurementDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public MeasurementDTO showById(@PathVariable("id") int id) {
        return measurementsService.findById(id);
    }


    @GetMapping()
    public List<MeasurementDTO> showAll() {
        log.info("Take from db");
        return measurementsService.findAll();
    }

    @GetMapping("/rainyDaysCount")
    public List<MeasurementDTO> showAllRainDays() {
        return measurementsService.findAllRainingDays();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handlerException(MeasurementNotCreatedException e) {
        return new ResponseEntity<>(new MeasurementErrorResponse(e.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handlerException(SensorNotFoundException e) {
        return new ResponseEntity<>(new MeasurementErrorResponse("Sensor not found!", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handlerException(MeasurementNotFoundException e) {
        return new ResponseEntity<>(new MeasurementErrorResponse(e.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }


}
