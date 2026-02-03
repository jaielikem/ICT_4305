package com.university.parking;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a parking lot at the University.
 * Each parking lot has a unique ID, address, and capacity.
 * Tracks cars currently parked and their entry times.
 */
public class ParkingLot {
    private String lotId;
    private Address address;
    private int capacity;
    private Map<String, ParkingTransaction> currentlyParked; // license -> transaction
    
    /**
     * Constructs a ParkingLot with the specified attributes.
     * 
     * @param lotId the unique identifier for this parking lot
     * @param address the physical address of the parking lot
     * @param capacity the maximum number of cars this lot can hold
     */
    public ParkingLot(String lotId, Address address, int capacity) {
        this.lotId = lotId;
        this.address = address;
        this.capacity = capacity;
        this.currentlyParked = new HashMap<>();
    }
    
    /**
     * Records a car entering the parking lot.
     * Creates a parking transaction with the entry time.
     * 
     * @param car the car entering the lot
     * @throws IllegalStateException if the lot is at capacity
     * @throws IllegalArgumentException if the car's permit is invalid
     */
    public void entry(Car car) {
        // Check if lot is full
        if (currentlyParked.size() >= capacity) {
            throw new IllegalStateException("Parking lot is at capacity");
        }
        
        // Verify the car has a valid permit
        if (!car.isPermitValid()) {
            throw new IllegalArgumentException("Car does not have a valid permit");
        }
        
        // Check if car is already parked
        if (currentlyParked.containsKey(car.getLicense())) {
            throw new IllegalArgumentException("Car is already parked in this lot");
        }
        
        // Create a new parking transaction
        ParkingTransaction transaction = new ParkingTransaction(
            car.getLicense(),
            car.getPermit(),
            Instant.now(),
            lotId,
            car.getType()
        );
        
        // Add to currently parked cars
        currentlyParked.put(car.getLicense(), transaction);
    }
    
    /**
     * Records a car exiting the parking lot.
     * 
     * @param license the license plate of the exiting car
     * @return the completed ParkingTransaction
     * @throws IllegalArgumentException if the car is not currently parked
     */
    public ParkingTransaction exit(String license) {
        ParkingTransaction transaction = currentlyParked.remove(license);
        
        if (transaction == null) {
            throw new IllegalArgumentException("Car with license " + license + " is not parked in this lot");
        }
        
        // Set exit time
        transaction.setExitTime(Instant.now());
        
        return transaction;
    }
    
    /**
     * Gets the current number of cars parked in the lot.
     * 
     * @return the number of parked cars
     */
    public int getCurrentOccupancy() {
        return currentlyParked.size();
    }
    
    /**
     * Gets the number of available parking spaces.
     * 
     * @return the number of available spaces
     */
    public int getAvailableSpaces() {
        return capacity - currentlyParked.size();
    }
    
    /**
     * Checks if a specific car is currently parked in this lot.
     * 
     * @param license the license plate to check
     * @return true if the car is parked, false otherwise
     */
    public boolean isCarParked(String license) {
        return currentlyParked.containsKey(license);
    }
    
    /**
     * Gets the parking transaction for a currently parked car.
     * 
     * @param license the license plate
     * @return the ParkingTransaction, or null if not parked
     */
    public ParkingTransaction getTransaction(String license) {
        return currentlyParked.get(license);
    }
    
    // Getters
    public String getLotId() {
        return lotId;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    // Setters
    public void setLotId(String lotId) {
        this.lotId = lotId;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    @Override
    public String toString() {
        return "ParkingLot{" +
                "lotId='" + lotId + '\'' +
                ", address=" + address +
                ", capacity=" + capacity +
                ", currentOccupancy=" + currentlyParked.size() +
                ", availableSpaces=" + getAvailableSpaces() +
                '}';
    }
}
