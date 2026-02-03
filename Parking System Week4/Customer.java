package com.university.parking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer registered with the University parking office.
 * Customers can register multiple cars and receive permits for each.
 */
public class Customer {
    private String customerId;
    private String name;
    private Address address;
    private String phoneNumber;
    private List<Car> registeredCars;
    private static int permitCounter = 1000; // For generating unique permit numbers
    
    /**
     * Constructs a Customer with all required information.
     * 
     * @param customerId the unique customer identifier
     * @param name the customer's name
     * @param address the customer's address
     * @param phoneNumber the customer's phone number
     */
    public Customer(String customerId, String name, Address address, String phoneNumber) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.registeredCars = new ArrayList<>();
    }
    
    /**
     * Registers a new car for this customer and assigns a parking permit.
     * The permit is valid for one year from the registration date.
     * 
     * @param license the license plate number of the car
     * @param type the type of car (COMPACT or SUV)
     * @return the newly registered Car object
     */
    public Car register(String license, CarType type) {
        // Create the car
        Car car = new Car(license, type, this.customerId);
        
        // Generate a unique permit number
        String permitNumber = "PERMIT-" + (permitCounter++);
        
        // Set permit expiration to one year from now
        LocalDate expiration = LocalDate.now().plusYears(1);
        
        // Assign the permit to the car
        car.assignPermit(permitNumber, expiration);
        
        // Add to the customer's list of cars
        registeredCars.add(car);
        
        return car;
    }
    
    /**
     * Gets all cars registered to this customer.
     * 
     * @return a list of registered cars
     */
    public List<Car> getRegisteredCars() {
        return new ArrayList<>(registeredCars);
    }
    
    /**
     * Finds a car by its license plate number.
     * 
     * @param license the license plate to search for
     * @return the Car object if found, null otherwise
     */
    public Car findCarByLicense(String license) {
        for (Car car : registeredCars) {
            if (car.getLicense().equals(license)) {
                return car;
            }
        }
        return null;
    }
    
    // Getters
    public String getCustomerId() {
        return customerId;
    }
    
    public String getName() {
        return name;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    // Setters
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", registeredCars=" + registeredCars.size() +
                '}';
    }
}
