package com.university.parking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Unit tests for the Customer class.
 */
public class CustomerTest {
    
    private Customer customer;
    private Address address;
    
    @BeforeEach
    public void setUp() {
        address = new Address("123 University Ave", "Denver", "CO", "80208");
        customer = new Customer("CUST001", "John Smith", address, "303-555-1234");
    }
    
    @Test
    public void testCustomerConstructor() {
        assertEquals("CUST001", customer.getCustomerId());
        assertEquals("John Smith", customer.getName());
        assertEquals(address, customer.getAddress());
        assertEquals("303-555-1234", customer.getPhoneNumber());
        assertEquals(0, customer.getRegisteredCars().size());
    }
    
    @Test
    public void testRegisterCar() {
        Car car = customer.register("ABC123", CarType.COMPACT);
        
        assertNotNull(car);
        assertEquals("ABC123", car.getLicense());
        assertEquals(CarType.COMPACT, car.getType());
        assertEquals("CUST001", car.getOwner());
        assertNotNull(car.getPermit());
        assertNotNull(car.getPermitExpiration());
        assertTrue(car.isPermitValid());
    }
    
    @Test
    public void testRegisterMultipleCars() {
        Car car1 = customer.register("ABC123", CarType.COMPACT);
        Car car2 = customer.register("XYZ789", CarType.SUV);
        
        List<Car> cars = customer.getRegisteredCars();
        assertEquals(2, cars.size());
        assertTrue(cars.contains(car1));
        assertTrue(cars.contains(car2));
    }
    
    @Test
    public void testRegisterCarGeneratesUniquePermits() {
        Car car1 = customer.register("ABC123", CarType.COMPACT);
        Car car2 = customer.register("XYZ789", CarType.SUV);
        
        assertNotEquals(car1.getPermit(), car2.getPermit());
    }
    
    @Test
    public void testFindCarByLicense() {
        customer.register("ABC123", CarType.COMPACT);
        customer.register("XYZ789", CarType.SUV);
        
        Car found = customer.findCarByLicense("ABC123");
        assertNotNull(found);
        assertEquals("ABC123", found.getLicense());
        
        Car notFound = customer.findCarByLicense("INVALID");
        assertNull(notFound);
    }
    
    @Test
    public void testGetRegisteredCarsReturnsNewList() {
        customer.register("ABC123", CarType.COMPACT);
        List<Car> cars1 = customer.getRegisteredCars();
        List<Car> cars2 = customer.getRegisteredCars();
        
        // Should return different list objects
        assertNotSame(cars1, cars2);
        // But with the same content
        assertEquals(cars1.size(), cars2.size());
    }
    
    @Test
    public void testSetters() {
        Address newAddress = new Address("456 Oak St", "Boulder", "CO", "80301");
        customer.setName("Jane Doe");
        customer.setAddress(newAddress);
        customer.setPhoneNumber("303-555-5678");
        
        assertEquals("Jane Doe", customer.getName());
        assertEquals(newAddress, customer.getAddress());
        assertEquals("303-555-5678", customer.getPhoneNumber());
    }
    
    @Test
    public void testToString() {
        customer.register("ABC123", CarType.COMPACT);
        String result = customer.toString();
        
        assertTrue(result.contains("CUST001"));
        assertTrue(result.contains("John Smith"));
        assertTrue(result.contains("1")); // 1 registered car
    }
}
