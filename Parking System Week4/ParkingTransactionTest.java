package com.university.parking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;
import java.time.Duration;

/**
 * Unit tests for the ParkingTransaction class.
 */
public class ParkingTransactionTest {
    
    private ParkingTransaction transaction;
    private Instant entryTime;
    
    @BeforeEach
    public void setUp() {
        entryTime = Instant.now().minus(Duration.ofHours(2).plusMinutes(30));
        transaction = new ParkingTransaction("ABC123", "PERMIT-001", entryTime, "LOT-A", CarType.COMPACT);
    }
    
    @Test
    public void testTransactionConstructor() {
        assertEquals("ABC123", transaction.getLicense());
        assertEquals("PERMIT-001", transaction.getPermit());
        assertEquals(entryTime, transaction.getEntryTime());
        assertEquals("LOT-A", transaction.getLotId());
        assertEquals(CarType.COMPACT, transaction.getCarType());
        assertNull(transaction.getExitTime());
    }
    
    @Test
    public void testGetParkingDurationWithoutExit() {
        Duration duration = transaction.getParkingDuration();
        // Should be approximately 2.5 hours
        assertTrue(duration.toHours() >= 2);
        assertTrue(duration.toHours() <= 3);
    }
    
    @Test
    public void testGetParkingDurationWithExit() {
        Instant exitTime = entryTime.plus(Duration.ofHours(3));
        transaction.setExitTime(exitTime);
        
        Duration duration = transaction.getParkingDuration();
        assertEquals(3, duration.toHours());
    }
    
    @Test
    public void testGetHoursParkedRoundsUp() {
        // Entry was 2.5 hours ago, should round up to 3
        long hours = transaction.getHoursParked();
        assertEquals(3, hours);
    }
    
    @Test
    public void testGetHoursParkedExactHours() {
        Instant exactEntry = Instant.now().minus(Duration.ofHours(4));
        ParkingTransaction exactTransaction = new ParkingTransaction(
            "XYZ789", "PERMIT-002", exactEntry, "LOT-B", CarType.SUV);
        exactTransaction.setExitTime(Instant.now());
        
        long hours = exactTransaction.getHoursParked();
        assertEquals(4, hours);
    }
    
    @Test
    public void testSetExitTime() {
        Instant exitTime = Instant.now();
        transaction.setExitTime(exitTime);
        
        assertEquals(exitTime, transaction.getExitTime());
    }
    
    @Test
    public void testToString() {
        String result = transaction.toString();
        assertTrue(result.contains("ABC123"));
        assertTrue(result.contains("PERMIT-001"));
        assertTrue(result.contains("LOT-A"));
        assertTrue(result.contains("COMPACT"));
    }
}
