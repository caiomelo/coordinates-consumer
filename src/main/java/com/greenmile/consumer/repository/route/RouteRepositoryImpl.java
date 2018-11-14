package com.greenmile.consumer.repository.route;

import com.greenmile.consumer.model.route.Route;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author caioalbmelo
 */
@Repository
public class RouteRepositoryImpl implements RouteRepositoryCustom {

    @Autowired
    private MongoTemplate template;

    @Cacheable("route.by.vehicleId")
    @Override
    public Route findByVehicleId(String vehicleId) {
        return template.findOne(Query.query(Criteria.where("assignedVehicle").is(vehicleId)), Route.class);
    }

    @Override
    public List<Route> findAll() {
        return template.findAll(Route.class);
    }
    
    

}
