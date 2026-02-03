package com.university.parking;

/**
 * Enum representing the types of cars supported by the parking system.
 * The University provides a 20% discount on compact cars compared to SUV cars.
 */
public enum CarType {
    COMPACT,
    SUV;
    
    /**
     * Returns the discount multiplier for this car type.
     * COMPACT cars receive a 0.8 multiplier (20% discount)
     * SUV cars receive a 1.0 multiplier (no discount)
     * 
     * @return the discount multiplier
     */
    public double getDiscountMultiplier() {
        switch (this) {
            case COMPACT:
                return 0.8; // 20% discount
            case SUV:
                return 1.0; // No discount
            default:
                return 1.0;
        }
    }
}
