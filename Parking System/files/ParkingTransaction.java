package com.university.parking;

import java.time.Instant;
import java.time.Duration;

/**
 * Represents a parking transaction, tracking when a car entered and exited a parking lot.
 * Used for calculating charges based on parking duration.
 */
public class ParkingTransaction {
    private String license;
    private String permit;
    private Instant entryTime;
    private Instant exitTime;
    private String lotId;
    private CarType carType;
    
    /**
     * Constructs a ParkingTransaction for a car entering a lot.
     * 
     * @param license the license plate of the car
     * @param permit the permit number
     * @param entryTime the time the car entered
     * @param lotId the parking lot identifier
     * @param carType the type of car
     */
    public ParkingTransaction(String license, String permit, Instant entryTime, String lotId, CarType carType) {
        this.license = license;
        this.permit = permit;
        this.entryTime = entryTime;
        this.lotId = lotId;
        this.carType = carType;
        this.exitTime = null;
    }
    
    /**
     * Calculates the duration the car has been/was parked.
     * 
     * @return the parking duration
     */
    public Duration getParkingDuration() {
        Instant end = (exitTime != null) ? exitTime : Instant.now();
        return Duration.between(entryTime, end);
    }
    
    /**
     * Calculates the number of hours parked (rounded up).
     * 
     * @return the number of hours
     */
    public long getHoursParked() {
        Duration duration = getParkingDuration();
        long hours = duration.toHours();
        // Round up if there are any remaining minutes
        if (duration.toMinutes() % 60 > 0) {
            hours++;
        }
        return hours;
    }
    
    // Getters
    public String getLicense() {
        return license;
    }
    
    public String getPermit() {
        return permit;
    }
    
    public Instant getEntryTime() {
        return entryTime;
    }
    
    public Instant getExitTime() {
        return exitTime;
    }
    
    public String getLotId() {
        return lotId;
    }
    
    public CarType getCarType() {
        return carType;
    }
    
    // Setters
    public void setExitTime(Instant exitTime) {
        this.exitTime = exitTime;
    }
    
    @Override
    public String toString() {
        return "ParkingTransaction{" +
                "license='" + license + '\'' +
                ", permit='" + permit + '\'' +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                ", lotId='" + lotId + '\'' +
                ", carType=" + carType +
                '}';
    }
}
