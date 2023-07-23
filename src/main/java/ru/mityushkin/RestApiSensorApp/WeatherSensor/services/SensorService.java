package ru.mityushkin.RestApiSensorApp.WeatherSensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.models.Sensor;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.repositories.SensorRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    public List<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }
}
