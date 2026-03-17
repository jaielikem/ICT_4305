package parking;

/**
 * Represents a customer's vehicle.
 */
public class Car {
    private final String licensePlate;
    private final CarType carType;

    public Car(String licensePlate, CarType carType) {
        if (licensePlate == null || licensePlate.isBlank()) {
            throw new IllegalArgumentException("License plate cannot be blank.");
        }
        this.licensePlate = licensePlate;
        this.carType      = carType;
    }

    public String getLicensePlate() { return licensePlate; }
    public CarType getCarType()     { return carType; }

    @Override
    public String toString() {
        return "Car[plate=" + licensePlate + ", type=" + carType + "]";
    }
}
