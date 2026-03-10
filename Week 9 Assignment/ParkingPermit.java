package parking;

import java.util.Objects;

/**
 * Represents a parking permit issued to a customer for a specific car.
 * Each car owned by a customer requires its own permit.
 */
public class ParkingPermit {

    private final String permitId;
    private final Customer customer;
    private final Car car;

    public ParkingPermit(String permitId, Customer customer, Car car) {
        this.permitId = permitId;
        this.customer = customer;
        this.car = car;
    }

    public String getPermitId() {
        return permitId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    /**
     * Two permits are equal if they share the same permitId.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingPermit)) return false;
        ParkingPermit permit = (ParkingPermit) o;
        return Objects.equals(permitId, permit.permitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permitId);
    }

    @Override
    public String toString() {
        return "ParkingPermit{permitId='" + permitId + "', customer=" + customer.getCustomerId()
                + ", car=" + car.getLicensePlate() + "}";
    }
}
