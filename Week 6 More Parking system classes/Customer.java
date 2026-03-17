package parking;

import java.util.UUID;

/**
 * Represents a customer registered with the parking office.
 */
public class Customer {
    private final String customerId;
    private String name;
    private Address address;
    private String phone;

    public Customer(String name, Address address, String phone) {
        this.customerId = UUID.randomUUID().toString();
        this.name    = name;
        this.address = address;
        this.phone   = phone;
    }

    public String getCustomerId() { return customerId; }
    public String getName()       { return name; }
    public Address getAddress()   { return address; }
    public String getPhone()      { return phone; }

    public void setName(String name)       { this.name = name; }
    public void setAddress(Address addr)   { this.address = addr; }
    public void setPhone(String phone)     { this.phone = phone; }

    @Override
    public String toString() {
        return "Customer{id='" + customerId + "', name='" + name +
               "', phone='" + phone + "', address=" + address + "}";
    }
}
