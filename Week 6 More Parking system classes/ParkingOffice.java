package parking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Central office that manages customers, cars, lots, permits, and charges.
 * Demonstrates Java Generics through typed List collections.
 */
public class ParkingOffice {
    private final String name;
    private final Address address;

    private final List<Customer>      customers;
    private final List<Car>           cars;
    private final List<ParkingLot>    lots;
    private final List<ParkingCharge> charges;
    private final List<ParkingPermit> permits;

    public ParkingOffice(String name, Address address) {
        this.name      = name;
        this.address   = address;
        this.customers = new ArrayList<>();
        this.cars      = new ArrayList<>();
        this.lots      = new ArrayList<>();
        this.charges   = new ArrayList<>();
        this.permits   = new ArrayList<>();
    }

    // ---------------------------------------------------------------
    // Registration
    // ---------------------------------------------------------------

    /**
     * Registers a new customer and returns the created Customer object.
     */
    public Customer register(String name, Address address, String phone) {
        Customer c = new Customer(name, address, phone);
        customers.add(c);
        return c;
    }

    /**
     * Registers a car for an existing customer and issues a ParkingPermit.
     */
    public ParkingPermit register(Customer c, String license, CarType type) {
        Car car = new Car(license, type, c.getCustomerId());
        cars.add(car);
        ParkingPermit permit = new ParkingPermit(car);
        permits.add(permit);
        return permit;
    }

    // ---------------------------------------------------------------
    // Lot management
    // ---------------------------------------------------------------

    public void addLot(ParkingLot lot) {
        lots.add(lot);
    }

    // ---------------------------------------------------------------
    // Charging
    // ---------------------------------------------------------------

    /**
     * Adds a ParkingCharge to the ledger and returns its amount.
     */
    public Money addCharge(ParkingCharge charge) {
        charges.add(charge);
        return charge.getAmount();
    }

    // ---------------------------------------------------------------
    // Lookup helpers
    // ---------------------------------------------------------------

    /**
     * Returns the first customer whose name matches (case-insensitive),
     * or null if not found.
     */
    public Customer getCustomer(String name) {
        return customers.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /** Returns all charges associated with a given permit ID. */
    public List<ParkingCharge> getCharges(String permitId) {
        List<ParkingCharge> result = new ArrayList<>();
        for (ParkingCharge ch : charges) {
            if (ch.getPermitId().equals(permitId)) result.add(ch);
        }
        return Collections.unmodifiableList(result);
    }

    /** Sums all charges for a given permit. */
    public Money getTotalCharges(String permitId) {
        return getCharges(permitId).stream()
                .map(ParkingCharge::getAmount)
                .reduce(new Money(0), Money::add);
    }

    // ---------------------------------------------------------------
    // Read-only accessors
    // ---------------------------------------------------------------

    public String getName()                       { return name; }
    public Address getAddress()                   { return address; }
    public List<Customer> getCustomers()          { return Collections.unmodifiableList(customers); }
    public List<Car> getCars()                    { return Collections.unmodifiableList(cars); }
    public List<ParkingLot> getLots()             { return Collections.unmodifiableList(lots); }
    public List<ParkingCharge> getCharges()       { return Collections.unmodifiableList(charges); }
    public List<ParkingPermit> getPermits()       { return Collections.unmodifiableList(permits); }

    @Override
    public String toString() {
        return "ParkingOffice{name='" + name + "', address=" + address + "}";
    }
}
