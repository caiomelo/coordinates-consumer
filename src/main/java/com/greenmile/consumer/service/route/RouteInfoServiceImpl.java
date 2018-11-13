package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.RouteInfo;
import com.greenmile.consumer.repository.route.RouteInfoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author caioalbmelo
 */
@Service
public class RouteInfoServiceImpl implements RouteInfoService {

    private RouteInfoRepository repository;

    @Autowired
    public void setRepository(RouteInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public RouteInfo findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void saveStatus(RouteInfo info) {
        repository.saveStatus(info);
    }

    @Override
    public List<RouteInfo> getAllStatuses() {
        return repository.getAllStatuses();
    }

    @Override
    public void saveExecutedStop(RouteInfo info) {
        repository.saveExecutedStop(info);
    }

    @Override
    public List<RouteInfo> getAllExecutedStops() {
        return repository.getAllExecutedStops();
    }

    @Override
    public void saveLongestStop(RouteInfo info) {
        repository.saveLongestStop(info);
    }

    @Override
    public List<RouteInfo> getAllLongestStops() {
        return repository.getAllLongestStops();
    }
}
