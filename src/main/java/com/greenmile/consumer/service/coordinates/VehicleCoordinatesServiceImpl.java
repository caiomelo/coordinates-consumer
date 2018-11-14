package com.greenmile.consumer.service.coordinates;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import com.greenmile.consumer.model.route.Route;
import com.greenmile.consumer.model.route.RouteStatus;
import com.greenmile.consumer.repository.coordinates.VehicleCoordinatesRepository;
import com.greenmile.consumer.service.route.RouteService;
import com.greenmile.consumer.service.route.RouteUpdateService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author caioalbmelo
 */
@Service
public class VehicleCoordinatesServiceImpl implements VehicleCoordinatesService {

    private VehicleCoordinatesRepository repository;

    private RouteService routeService;

    private RouteUpdateService routeUpdateService;

    @Autowired
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    @Autowired
    public void setRouteUpdateService(RouteUpdateService routeUpdateService) {
        this.routeUpdateService = routeUpdateService;
    }

    @Autowired
    public void setRepository(VehicleCoordinatesRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "coordinates",
            groupId = "coordinates-consumers",
            containerFactory = "coordinatesListenerContainerFactory")
    @Override
    public void receive(VehicleCoordinates coordinates) {
        Route route = routeService.findByVehicleId(coordinates.getVehicleId());
        if (route != null && !RouteStatus.FINISHED.equals(route.getStatus())) {
            repository.add(route, coordinates);
            Set<VehicleCoordinates> savedCoordinates = repository.getAllUntil(route, coordinates.getInstant().getTime());
            if (routeUpdateService.update(route, savedCoordinates)) {
                routeService.save(route);
            }
        }
    }
}
