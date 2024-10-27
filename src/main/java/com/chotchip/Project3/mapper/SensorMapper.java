package com.chotchip.Project3.mapper;

import com.chotchip.Project3.dto.SensorDTO;
import com.chotchip.Project3.models.Sensor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper
public interface SensorMapper {
    Sensor toEntity(SensorDTO sensorDTO);

    SensorDTO toDTO(Sensor sensor);

    String toString(SensorDTO sensorDTO);

}
