package com.chotchip.Project3.exception.response;

import java.time.LocalDateTime;

public class MeasurementErrorResponse {
    private String message;
    private LocalDateTime timeStamp;

    public MeasurementErrorResponse(String message, LocalDateTime timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
