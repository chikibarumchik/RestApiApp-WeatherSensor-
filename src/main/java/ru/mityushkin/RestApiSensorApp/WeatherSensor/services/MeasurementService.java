package ru.mityushkin.RestApiSensorApp.WeatherSensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.models.Measurement;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.models.Sensor;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.repositories.MeasurementRepository;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.repositories.SensorRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Transactional
    public void addMeasurement(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);

    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setCreated_at(LocalDateTime.now());
    }
}
