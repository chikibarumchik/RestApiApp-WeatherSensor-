package ru.mityushkin.RestApiSensorApp.WeatherSensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.DTO.MeasurementDTO;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.models.Measurement;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.services.MeasurementService;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.services.SensorService;
import ru.mityushkin.RestApiSensorApp.WeatherSensor.util.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
@Validated
public class MeasurementController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;
    private final SensorService sensorService;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator, SensorService sensorService) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
        this.sensorService = sensorService;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {
        measurementValidator.validate(measurementDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error: errors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");

            }

            throw new MeasurementNotCreatedException(errorMessage.toString());
        }

        Measurement measurement = convertToMeasurement(measurementDTO);
        measurementService.addMeasurement(measurement);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @GetMapping()
    public List<MeasurementDTO> findAll() {
        return measurementService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public String countRainyDays() {
        int rainyDays = 0;

        for (Measurement measurement: measurementService.findAll()) {
            if (measurement.isRaining()) {
                rainyDays += 1;
            }
        }

        return "Дождливых дней было: " + rainyDays;
    }



    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        //В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST );
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        measurement.setOwner(sensorService.findByName(measurementDTO.getSensor()).get(0));
        return measurement;
    }


}
