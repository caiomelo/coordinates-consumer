package com.greenmile.consumer.model.route;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author caioalbmelo
 */
public class PlannedStopTest {

    @Test
    public void testThatItReturnsMinLongIfStartingDateIsNotSet() {
        PlannedStop stop = new PlannedStop();
        assertEquals(Long.MIN_VALUE, stop.getDuration());
    }

    @Test
    public void testThatItReturnsMinLongIfFinishingDateIsNotSet() {
        PlannedStop stop = new PlannedStop();
        stop.setStartedDate(new Date());
        assertEquals(Long.MIN_VALUE, stop.getDuration());
    }

    @Test
    public void testThatItCanCorrectlyCalculateTheTimeItTookToFinished() {
        PlannedStop stop = new PlannedStop();
        stop.setStartedDate(new Date());
        stop.setFinishedDate(new Date(stop.getStartedDate().getTime() + 1000));
        assertEquals(1000, stop.getDuration());
    }

}
