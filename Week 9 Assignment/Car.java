package parking;

import java.util.Objects;

/**
 * Represents a car registered in the parking system.
 * A customer may own multiple cars, each with its own permit.
 */
public class Car {

    private final String licensePlate;
    private final CarType carType;

    public Car(String licensePlate, CarType carType) {
        this.licensePlate = licensePlate;
        this.carType = carType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public CarType getCarType() {
        return carType;
    }

    /**
     * Two cars are equal if they share the same license plate.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return Objects.equals(licensePlate, car.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate);
    }

    @Override
    public String toString() {
        return "Car{licensePlate='" + licensePlate + "', carType=" + carType + "}";
    }
}
