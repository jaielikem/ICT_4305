package com.university.parking;

/**
 * Represents a physical address with standard US address components.
 */
public class Address {
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;
    
    /**
     * Constructs an Address with all components.
     * 
     * @param streetAddress1 the primary street address
     * @param streetAddress2 the secondary street address (apartment, suite, etc.)
     * @param city the city
     * @param state the state
     * @param zipCode the ZIP code
     */
    public Address(String streetAddress1, String streetAddress2, String city, String state, String zipCode) {
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
    
    /**
     * Constructs an Address without a secondary street address.
     * 
     * @param streetAddress1 the primary street address
     * @param city the city
     * @param state the state
     * @param zipCode the ZIP code
     */
    public Address(String streetAddress1, String city, String state, String zipCode) {
        this(streetAddress1, "", city, state, zipCode);
    }
    
    /**
     * Returns a formatted string representation of the address.
     * 
     * @return the formatted address information
     */
    public String getAddressInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(streetAddress1);
        
        if (streetAddress2 != null && !streetAddress2.trim().isEmpty()) {
            sb.append(", ").append(streetAddress2);
        }
        
        sb.append(", ").append(city);
        sb.append(", ").append(state);
        sb.append(" ").append(zipCode);
        
        return sb.toString();
    }
    
    // Getters
    public String getStreetAddress1() {
        return streetAddress1;
    }
    
    public String getStreetAddress2() {
        return streetAddress2;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getState() {
        return state;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    // Setters
    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }
    
    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    @Override
    public String toString() {
        return getAddressInfo();
    }
}
