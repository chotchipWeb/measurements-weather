package com.chotchip.Project3.task2;

import com.chotchip.Project3.dto.MeasurementDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class AddMeasurementsThousend {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurements/add";
        String url2 = "http://localhost:8080/measurements";
        MeasurementDTO measurementDTO = new MeasurementDTO();
//        for (int i = 0; i < 100; i++) {
//            Random random = new Random();
//            int random1 = (int) Math.round(Math.random());
//            int random2 = (int) Math.round(Math.random());
//            if (random1 == 1) {
//                measurementDTO.setValue(random.nextInt(100) * -1);
//            } else {
//                measurementDTO.setValue(random.nextInt(100));
//            }
//
//            if (random2 == 1) {
//                measurementDTO.setRaining(true);
//            }
//            SensorDTO sensorDTO = new SensorDTO();
//            sensorDTO.setName("Test sensor");
//            measurementDTO.setSensor(sensorDTO);
//
//            restTemplate.postForEntity(url,measurementDTO, String.class);
//        }
        ResponseEntity<List> forEntity = restTemplate.getForEntity(url2, List.class);
        List body = forEntity.getBody();
        System.out.println(body + "\n "+ body.size());
    }
}
