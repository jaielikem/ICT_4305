package com.university.parking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 * Unit tests for the ParkingLot class.
 */
public class ParkingLotTest {
    
    private ParkingLot lot;
    private Car validCar;
    private Car invalidCar;
    
    @BeforeEach
    public void setUp() {
        Address lotAddress = new Address("100 Parking Way", "Denver", "CO", "80208");
        lot = new ParkingLot("LOT-A", lotAddress, 5);
        
        // Car with valid permit
        validCar = new Car("PERMIT-001", LocalDate.now().plusMonths(6), "ABC123", CarType.COMPACT, "CUST001");
        
        // Car with expired permit
        invalidCar = new Car("PERMIT-002", LocalDate.now().minusDays(1), "XYZ789", CarType.SUV, "CUST002");
    }
    
    @Test
    public void testParkingLotConstructor() {
        assertEquals("LOT-A", lot.getLotId());
        assertEquals(5, lot.getCapacity());
        assertEquals(0, lot.getCurrentOccupancy());
        assertEquals(5, lot.getAvailableSpaces());
    }
    
    @Test
    public void testEntryWithValidCar() {
        assertDoesNotThrow(() -> lot.entry(validCar));
        assertEquals(1, lot.getCurrentOccupancy());
        assertEquals(4, lot.getAvailableSpaces());
        assertTrue(lot.isCarParked("ABC123"));
    }
    
    @Test
    public void testEntryWithInvalidPermit() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lot.entry(invalidCar);
        });
        assertTrue(exception.getMessage().contains("valid permit"));
    }
    
    @Test
    public void testEntryWhenLotIsFull() {
        // Fill the lot
        for (int i = 0; i < 5; i++) {
            Car car = new Car("PERMIT-" + i, LocalDate.now().plusMonths(6), 
                            "LIC" + i, CarType.COMPACT, "CUST" + i);
            lot.entry(car);
        }
        
        // Try to add one more
        Car extraCar = new Car("PERMIT-999", LocalDate.now().plusMonths(6), 
                              "EXTRA", CarType.COMPACT, "CUST999");
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            lot.entry(extraCar);
        });
        assertTrue(exception.getMessage().contains("capacity"));
    }
    
    @Test
    public void testEntryWithAlreadyParkedCar() {
        lot.entry(validCar);
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lot.entry(validCar);
        });
        assertTrue(exception.getMessage().contains("already parked"));
    }
    
    @Test
    public void testExit() {
        lot.entry(validCar);
        assertTrue(lot.isCarParked("ABC123"));
        
        ParkingTransaction transaction = lot.exit("ABC123");
        
        assertNotNull(transaction);
        assertEquals("ABC123", transaction.getLicense());
        assertNotNull(transaction.getExitTime());
        assertFalse(lot.isCarParked("ABC123"));
        assertEquals(0, lot.getCurrentOccupancy());
    }
    
    @Test
    public void testExitCarNotParked() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lot.exit("NOTHERE");
        });
        assertTrue(exception.getMessage().contains("not parked"));
    }
    
    @Test
    public void testGetTransaction() {
        lot.entry(validCar);
        
        ParkingTransaction transaction = lot.getTransaction("ABC123");
        assertNotNull(transaction);
        assertEquals("ABC123", transaction.getLicense());
        assertEquals("LOT-A", transaction.getLotId());
        
        ParkingTransaction notFound = lot.getTransaction("NOTHERE");
        assertNull(notFound);
    }
    
    @Test
    public void testIsCarParked() {
        assertFalse(lot.isCarParked("ABC123"));
        lot.entry(validCar);
        assertTrue(lot.isCarParked("ABC123"));
        lot.exit("ABC123");
        assertFalse(lot.isCarParked("ABC123"));
    }
    
    @Test
    public void testSetters() {
        Address newAddress = new Address("200 New Parking", "Boulder", "CO", "80301");
        lot.setLotId("LOT-B");
        lot.setAddress(newAddress);
        lot.setCapacity(10);
        
        assertEquals("LOT-B", lot.getLotId());
        assertEquals(newAddress, lot.getAddress());
        assertEquals(10, lot.getCapacity());
    }
    
    @Test
    public void testToString() {
        String result = lot.toString();
        assertTrue(result.contains("LOT-A"));
        assertTrue(result.contains("5")); // capacity
    }
}
