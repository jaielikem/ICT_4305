package com.university.parking;

import java.time.LocalDate;

/**
 * Represents a car registered in the university parking system.
 * Each car has a permit, license plate, type, and owner.
 */
public class Car {
    private String permit;
    private LocalDate permitExpiration;
    private String license;
    private CarType type;
    private String owner; // customer id
    
    /**
     * Constructs a Car with all attributes.
     * 
     * @param permit the parking permit number
     * @param permitExpiration the expiration date of the permit
     * @param license the license plate number
     * @param type the type of car (COMPACT or SUV)
     * @param owner the customer ID of the owner
     */
    public Car(String permit, LocalDate permitExpiration, String license, CarType type, String owner) {
        this.permit = permit;
        this.permitExpiration = permitExpiration;
        this.license = license;
        this.type = type;
        this.owner = owner;
    }
    
    /**
     * Constructs a Car without permit information (to be assigned later).
     * 
     * @param license the license plate number
     * @param type the type of car (COMPACT or SUV)
     * @param owner the customer ID of the owner
     */
    public Car(String license, CarType type, String owner) {
        this.license = license;
        this.type = type;
        this.owner = owner;
        this.permit = null;
        this.permitExpiration = null;
    }
    
    /**
     * Checks if the permit is currently valid.
     * 
     * @return true if the permit exists and has not expired, false otherwise
     */
    public boolean isPermitValid() {
        if (permit == null || permitExpiration == null) {
            return false;
        }
        return !LocalDate.now().isAfter(permitExpiration);
    }
    
    /**
     * Assigns or updates the permit information for this car.
     * 
     * @param permit the permit number
     * @param permitExpiration the expiration date
     */
    public void assignPermit(String permit, LocalDate permitExpiration) {
        this.permit = permit;
        this.permitExpiration = permitExpiration;
    }
    
    // Getters
    public String getPermit() {
        return permit;
    }
    
    public LocalDate getPermitExpiration() {
        return permitExpiration;
    }
    
    public String getLicense() {
        return license;
    }
    
    public CarType getType() {
        return type;
    }
    
    public String getOwner() {
        return owner;
    }
    
    // Setters
    public void setPermit(String permit) {
        this.permit = permit;
    }
    
    public void setPermitExpiration(LocalDate permitExpiration) {
        this.permitExpiration = permitExpiration;
    }
    
    public void setLicense(String license) {
        this.license = license;
    }
    
    public void setType(CarType type) {
        this.type = type;
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    @Override
    public String toString() {
        return "Car{" +
                "permit='" + permit + '\'' +
                ", permitExpiration=" + permitExpiration +
                ", license='" + license + '\'' +
                ", type=" + type +
                ", owner='" + owner + '\'' +
                '}';
    }
}
