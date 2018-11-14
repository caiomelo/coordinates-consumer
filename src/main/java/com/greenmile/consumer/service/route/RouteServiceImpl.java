package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.route.Route;
import com.greenmile.consumer.repository.route.RouteRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author caioalbmelo
 */
@Service
public class RouteServiceImpl implements RouteService {

    private RouteRepository repository;

    @Autowired
    public void setRepository(RouteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Route> findOne(String id) {
        return repository.findById(id);
    }

    @Override
    public Route save(Route route) {
        return repository.save(route);
    }

    @Override
    public Route findByVehicleId(String vehicleId) {
        return repository.findByVehicleId(vehicleId);
    }

}
