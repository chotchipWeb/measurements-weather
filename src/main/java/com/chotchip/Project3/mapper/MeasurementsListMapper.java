package com.chotchip.Project3.mapper;

import com.chotchip.Project3.dto.MeasurementDTO;
import com.chotchip.Project3.models.Measurement;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = MeasurementsMapper.class)
public interface MeasurementsListMapper {
    List<MeasurementDTO> toDTOList(List<Measurement> measurements);

    List<Measurement> toEntityList(List<MeasurementDTO> measurementDTOList);
}
