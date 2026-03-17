package parking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a registered parking customer.
 */
public class Customer {
    private static int nextId = 1000;

    private final String customerId;
    private final String name;
    private final String phoneNumber;
    private final Address address;
    private final List<Car> cars;

    public Customer(String name, String phoneNumber, Address address) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be blank.");
        }
        this.customerId  = "CUST-" + nextId++;
        this.name        = name;
        this.phoneNumber = phoneNumber;
        this.address     = address;
        this.cars        = new ArrayList<>();
    }

    public String getCustomerId()  { return customerId; }
    public String getName()        { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public Address getAddress()    { return address; }

    public void addCar(Car car) {
        if (car != null) cars.add(car);
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }

    @Override
    public String toString() {
        return "Customer[id=" + customerId + ", name=" + name + "]";
    }
}
