package com.greenmile.info.provider.service;

import com.greenmile.info.provider.model.RouteInfo;
import com.greenmile.info.provider.repository.RouteInfoRepository;
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
    public List<RouteInfo> findAll() {
        return repository.findAll();
    }

    @Override
    public RouteInfo findOne(String id) {
        return repository.findById(id);
    }

    @Override
    public void save(RouteInfo info) {
        repository.save(info);
    }

}
