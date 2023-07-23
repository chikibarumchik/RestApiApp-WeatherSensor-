package ru.mityushkin.RestApiSensorApp.WeatherSensor.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.DTO.MeasurementDTO;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.models.Measurement;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.models.Sensor;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.services.SensorService;

@Component
public class MeasurementValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;

        if (sensorService.findByName(measurementDTO.getSensor()).size() == 0) {
            errors.rejectValue("sensor", "", "Такого сенсора не существует существует");
        }
    }
}
