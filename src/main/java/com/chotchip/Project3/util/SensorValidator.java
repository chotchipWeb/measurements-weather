package com.chotchip.Project3.util;

import com.chotchip.Project3.dto.SensorDTO;
import com.chotchip.Project3.exception.SensorNotCreatedException;
import com.chotchip.Project3.models.Sensor;
import com.chotchip.Project3.services.SensorsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;
        Sensor byName = sensorsService.findByName(sensorDTO.getName());
        if (byName == null)
            throw new SensorNotCreatedException("name is not null");

        if (byName.getName().equals(sensorDTO.getName())) errors.rejectValue("name", "", "name already exists");

    }
}
