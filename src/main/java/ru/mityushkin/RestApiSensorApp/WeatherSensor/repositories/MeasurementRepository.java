package ru.mityushkin.RestApiSensorApp.WeatherSensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.models.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
