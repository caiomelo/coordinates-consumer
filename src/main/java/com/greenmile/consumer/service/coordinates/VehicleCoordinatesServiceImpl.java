package com.greenmile.consumer.service.coordinates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author caioalbmelo
 */
@Service
public class VehicleCoordinatesServiceImpl implements VehicleCoordinatesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleCoordinatesServiceImpl.class);

    @KafkaListener(topics = "coordinates",
            groupId = "coordinates-consumers",
            containerFactory = "coordinatesListenerContainerFactory")
    @Override
    public void receive(VehicleCoordinates coordinates) {
        try {
            LOGGER.info("Received coordinates: {}", new ObjectMapper().writeValueAsString(coordinates));
            //if it' the first coordinate for the route, send status change message
            //else, if it was already in a stop, verify whether it' finished
            //if it' finished, send add executed stop message
            //also if it's finished, verify whether it took longer than the current longest and send longest message
            //also if it's finished, verify whether it was the last stop and send status update change
            //
            //what to do? save new coordinate, verify what kind of update is it and inform info-provider?
        } catch (JsonProcessingException ex) {
            LOGGER.warn(("Could not parse coordinates to string"));
        }
    }

}
