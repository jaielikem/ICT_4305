package com.university.parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CarType enum.
 */
public class CarTypeTest {
    
    @Test
    public void testCompactDiscountMultiplier() {
        assertEquals(0.8, CarType.COMPACT.getDiscountMultiplier(), 0.001);
    }
    
    @Test
    public void testSUVDiscountMultiplier() {
        assertEquals(1.0, CarType.SUV.getDiscountMultiplier(), 0.001);
    }
    
    @Test
    public void testCompactHasTwentyPercentDiscount() {
        // COMPACT should be 20% less than SUV
        double compactMultiplier = CarType.COMPACT.getDiscountMultiplier();
        double suvMultiplier = CarType.SUV.getDiscountMultiplier();
        
        assertEquals(0.2, suvMultiplier - compactMultiplier, 0.001);
    }
    
    @Test
    public void testEnumValues() {
        CarType[] types = CarType.values();
        assertEquals(2, types.length);
        assertEquals(CarType.COMPACT, types[0]);
        assertEquals(CarType.SUV, types[1]);
    }
    
    @Test
    public void testValueOf() {
        assertEquals(CarType.COMPACT, CarType.valueOf("COMPACT"));
        assertEquals(CarType.SUV, CarType.valueOf("SUV"));
    }
}
