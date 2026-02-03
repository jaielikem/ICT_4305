package com.university.parking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 * Unit tests for the Car class.
 */
public class CarTest {
    
    private Car car1;
    private Car car2;
    
    @BeforeEach
    public void setUp() {
        // Car with permit
        car1 = new Car("PERMIT-001", LocalDate.now().plusMonths(6), "ABC123", CarType.COMPACT, "CUST001");
        
        // Car without permit
        car2 = new Car("XYZ789", CarType.SUV, "CUST002");
    }
    
    @Test
    public void testCarConstructorWithPermit() {
        assertEquals("PERMIT-001", car1.getPermit());
        assertEquals("ABC123", car1.getLicense());
        assertEquals(CarType.COMPACT, car1.getType());
        assertEquals("CUST001", car1.getOwner());
        assertNotNull(car1.getPermitExpiration());
    }
    
    @Test
    public void testCarConstructorWithoutPermit() {
        assertNull(car2.getPermit());
        assertNull(car2.getPermitExpiration());
        assertEquals("XYZ789", car2.getLicense());
        assertEquals(CarType.SUV, car2.getType());
        assertEquals("CUST002", car2.getOwner());
    }
    
    @Test
    public void testIsPermitValidWithValidPermit() {
        assertTrue(car1.isPermitValid());
    }
    
    @Test
    public void testIsPermitValidWithoutPermit() {
        assertFalse(car2.isPermitValid());
    }
    
    @Test
    public void testIsPermitValidWithExpiredPermit() {
        Car expiredCar = new Car("PERMIT-999", LocalDate.now().minusDays(1), "OLD123", CarType.COMPACT, "CUST003");
        assertFalse(expiredCar.isPermitValid());
    }
    
    @Test
    public void testAssignPermit() {
        LocalDate expiration = LocalDate.now().plusYears(1);
        car2.assignPermit("PERMIT-002", expiration);
        
        assertEquals("PERMIT-002", car2.getPermit());
        assertEquals(expiration, car2.getPermitExpiration());
        assertTrue(car2.isPermitValid());
    }
    
    @Test
    public void testSetters() {
        car1.setLicense("NEW123");
        car1.setType(CarType.SUV);
        car1.setOwner("CUST999");
        
        assertEquals("NEW123", car1.getLicense());
        assertEquals(CarType.SUV, car1.getType());
        assertEquals("CUST999", car1.getOwner());
    }
    
    @Test
    public void testToString() {
        String result = car1.toString();
        assertTrue(result.contains("ABC123"));
        assertTrue(result.contains("PERMIT-001"));
        assertTrue(result.contains("COMPACT"));
    }
}
