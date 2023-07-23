package ru.mityushkin.RestApiSensorApp.WeatherSensor.util;

import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.models.Sensor;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.services.SensorService;

@Component
public class SensorValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorService.findByName(sensor.getName()).size() != 0) {
            errors.rejectValue("name", "", "Такой сенсор уже существует");
        }
    }
}
