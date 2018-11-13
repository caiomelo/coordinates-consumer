package com.greenmile.info.provider.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 *
 * @author caioalbmelo
 */
public class RouteInfoTest {

    @Test
    public void testThatItExcludesEmptyListFromRouteInfoWhenSerializing() throws JsonProcessingException {
        RouteInfo infoWithEmptyExecutedStops = new RouteInfo();
        infoWithEmptyExecutedStops.setId(UUID.randomUUID().toString());
        infoWithEmptyExecutedStops.setLongest(new PlannedStop());

        String json = new ObjectMapper().writeValueAsString(infoWithEmptyExecutedStops);
        assertFalse(json.contains("executedStops"));
    }

    @Test
    public void testThatItExcludesNullValuesFromRouteInfoWhenSerializing() throws JsonProcessingException {
        PlannedStop stop1 = new PlannedStop();
        PlannedStop stop2 = new PlannedStop();

        List<PlannedStop> executedStops = new ArrayList<>();
        executedStops.add(stop1);
        executedStops.add(stop2);

        RouteInfo infoWithoutIdAndLongestStop = new RouteInfo();
        infoWithoutIdAndLongestStop.setExecutedStops(executedStops);

        String json = new ObjectMapper().writeValueAsString(infoWithoutIdAndLongestStop);
        assertFalse(json.contains("id"));
        assertFalse(json.contains("longestStop"));
    }

}
