package com.chotchip.Project3.config;

import com.chotchip.Project3.mapper.MeasurementsListMapper;
import com.chotchip.Project3.mapper.MeasurementsMapper;
import com.chotchip.Project3.mapper.SensorMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public SensorMapper sensorMapper() {
        return Mappers.getMapper(SensorMapper.class);
    }

    @Bean
    public MeasurementsMapper measurementsMapper() {
        return Mappers.getMapper(MeasurementsMapper.class);
    }

    @Bean
    public MeasurementsListMapper measurementsListMapper() {
        return Mappers.getMapper(MeasurementsListMapper.class);
    }
}
