package com.chotchip.Project3.controllers;

import com.chotchip.Project3.dto.SensorDTO;
import com.chotchip.Project3.exception.SensorNotCreatedException;
import com.chotchip.Project3.exception.SensorNotFoundException;
import com.chotchip.Project3.exception.response.SensorErrorResponse;
import com.chotchip.Project3.models.Sensor;
import com.chotchip.Project3.services.SensorsService;
import com.chotchip.Project3.util.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorsService sensorsService;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> save(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        sensorValidator.validate(sensorDTO, bindingResult);
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
            throw new SensorNotCreatedException(stringBuilder.toString());
        }

        sensorsService.save(convertToSensor(sensorDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public SensorDTO findById(@PathVariable("id") int id) {

        return convertToSensorDTO(sensorsService.findById(id));
    }



    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handlerException(SensorNotCreatedException e) {

        return new ResponseEntity<>(new SensorErrorResponse(e.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handlerException(SensorNotFoundException e) {
        String str = "Sensor with such id not found !";
        return new ResponseEntity<>(new SensorErrorResponse(str, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
