package ru.mityushkin.RestApiSensorApp.WeatherSensor.util;

public class SensorNotCreatedException extends RuntimeException{

    public SensorNotCreatedException(String msg) {
        super(msg);
    }
}
