package com.chotchip.Project3.mapper;

import com.chotchip.Project3.dto.MeasurementDTO;
import com.chotchip.Project3.models.Measurement;
import org.mapstruct.Mapper;

@Mapper
public interface MeasurementsMapper {
    Measurement toEntity(MeasurementDTO measurementDTO);

    MeasurementDTO toDTO(Measurement measurement);

}
