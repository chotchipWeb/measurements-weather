package com.chotchip.Project3.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MeasurementDTO {

    @Min(value = -100, message = "Min value -100!")
    @Max(value = 100, message = "Max value 100!")
    private int value;

    private boolean raining;
    private SensorDTO sensor;
}
