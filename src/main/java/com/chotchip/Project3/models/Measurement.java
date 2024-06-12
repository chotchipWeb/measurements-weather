package com.chotchip.Project3.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")

public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "value")
    @Min(value = -100, message = "Min value -100!")
    @Max(value = 100, message = "Max value 100!")
    private int value;
    @Column(name = "raining")
    private boolean raining;
    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    public Measurement() {

    }

    public Measurement(int value, boolean raining, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", value=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor +
                '}';
    }

    public int getId() {
        return this.id;
    }

    public @Min(value = -100, message = "Min value -100!") @Max(value = 100, message = "Max value 100!") int getValue() {
        return this.value;
    }

    public boolean isRaining() {
        return this.raining;
    }

    public Sensor getSensor() {
        return this.sensor;
    }

    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(@Min(value = -100, message = "Min value -100!") @Max(value = 100, message = "Max value 100!") int value) {
        this.value = value;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
