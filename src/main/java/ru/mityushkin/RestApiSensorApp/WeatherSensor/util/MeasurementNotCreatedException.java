package ru.mityushkin.RestApiSensorApp.WeatherSensor.util;

public class MeasurementNotCreatedException extends RuntimeException{
    public MeasurementNotCreatedException(String msg) {
        super(msg);
    }
}
