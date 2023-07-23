package ru.mityushkin.RestApiSensorApp.WeatherSensor.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class MeasurementDTO {

    @NotNull(message = "Введите показания температуры")
    @Min(value = -100, message = "Температура не может быть ниже -100 градусов")
    @Max(value = 100, message = "Температура не может быть выше 100 градусов")
    private double value;

    @NotNull(message = "Сенсор дождя не отработал")
    private boolean raining;

    @NotEmpty(message = "Введите имя сенсора")
    private String sensor;

    @JsonProperty("sensor")
    private void unpackNested(Map brand) {
        this.sensor = (String) brand.get("name");
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }
}
