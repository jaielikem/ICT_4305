package parking;

/**
 * Represents a car registered by a customer.
 */
public class Car {
    private final String license;
    private final CarType type;
    private final String ownerId;   // references Customer.customerId

    public Car(String license, CarType type, String ownerId) {
        this.license = license;
        this.type    = type;
        this.ownerId = ownerId;
    }

    public String getLicense() { return license; }
    public CarType getType()   { return type; }
    public String getOwnerId() { return ownerId; }

    @Override
    public String toString() {
        return "Car{license='" + license + "', type=" + type +
               ", ownerId='" + ownerId + "'}";
    }
}
