package com.chotchip.Project3.exception.measurement;

public class MeasurementNotFoundException extends RuntimeException {
   static final String MESSAGE = "Measurement not found!";
    public MeasurementNotFoundException() {
        super(MESSAGE);
    }
}
