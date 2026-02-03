package com.university.parking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Address class.
 */
public class AddressTest {
    
    private Address address1;
    private Address address2;
    
    @BeforeEach
    public void setUp() {
        address1 = new Address("123 Main St", "Apt 4B", "Denver", "CO", "80202");
        address2 = new Address("456 Oak Ave", "Springfield", "IL", "62701");
    }
    
    @Test
    public void testAddressConstructorWithAllFields() {
        assertEquals("123 Main St", address1.getStreetAddress1());
        assertEquals("Apt 4B", address1.getStreetAddress2());
        assertEquals("Denver", address1.getCity());
        assertEquals("CO", address1.getState());
        assertEquals("80202", address1.getZipCode());
    }
    
    @Test
    public void testAddressConstructorWithoutSecondaryAddress() {
        assertEquals("456 Oak Ave", address2.getStreetAddress1());
        assertEquals("", address2.getStreetAddress2());
        assertEquals("Springfield", address2.getCity());
        assertEquals("IL", address2.getState());
        assertEquals("62701", address2.getZipCode());
    }
    
    @Test
    public void testGetAddressInfoWithSecondaryAddress() {
        String expected = "123 Main St, Apt 4B, Denver, CO 80202";
        assertEquals(expected, address1.getAddressInfo());
    }
    
    @Test
    public void testGetAddressInfoWithoutSecondaryAddress() {
        String expected = "456 Oak Ave, Springfield, IL 62701";
        assertEquals(expected, address2.getAddressInfo());
    }
    
    @Test
    public void testToString() {
        assertEquals(address1.getAddressInfo(), address1.toString());
    }
    
    @Test
    public void testSetters() {
        address1.setStreetAddress1("789 Elm St");
        address1.setCity("Boulder");
        address1.setState("CO");
        address1.setZipCode("80301");
        
        assertEquals("789 Elm St", address1.getStreetAddress1());
        assertEquals("Boulder", address1.getCity());
        assertEquals("CO", address1.getState());
        assertEquals("80301", address1.getZipCode());
    }
}
