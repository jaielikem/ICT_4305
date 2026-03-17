package parking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * The central class of the parking system.
 *
 * ParkingOffice coordinates customer/car registration, permit issuance,
 * and parking transactions by delegating to PermitManager and
 * TransactionManager respectively.
 */
public class ParkingOffice {

    // ------------------------------------------------------------------ data
    private final String parkingOfficeName;
    private final Address parkingOfficeAddress;
    private final List<Customer> listOfCustomers;
    private final List<ParkingLot> listOfParkingLots;

    // Managers that do the real work
    private final PermitManager     permitManager;
    private final TransactionManager transactionManager;

    // ------------------------------------------------------------ constructor
    public ParkingOffice(String parkingOfficeName, Address parkingOfficeAddress) {
        this.parkingOfficeName    = parkingOfficeName;
        this.parkingOfficeAddress = parkingOfficeAddress;
        this.listOfCustomers      = new ArrayList<>();
        this.listOfParkingLots    = new ArrayList<>();
        this.permitManager        = new PermitManager();
        this.transactionManager   = new TransactionManager();
    }

    // ---------------------------------------------------------------- queries
    public String getParkingOfficeName() {
        return parkingOfficeName;
    }

    public Address getParkingOfficeAddress() {
        return parkingOfficeAddress;
    }

    public List<Customer> getListOfCustomers() {
        return Collections.unmodifiableList(listOfCustomers);
    }

    public List<ParkingLot> getListOfParkingLots() {
        return Collections.unmodifiableList(listOfParkingLots);
    }

    // ---------------------------------------------------------- lot management
    /**
     * Adds a parking lot to the office's portfolio.
     */
    public void addParkingLot(ParkingLot lot) {
        if (lot != null) listOfParkingLots.add(lot);
    }

    // --------------------------------------------------------- registration
    /**
     * Registers a new customer with the parking office.
     */
    public void register(Customer customer) {
        if (customer != null && !listOfCustomers.contains(customer)) {
            listOfCustomers.add(customer);
        }
    }

    /**
     * Registers a car for an existing customer and issues a ParkingPermit.
     *
     * @param customer the owner of the car
     * @param car      the car to register
     * @return the newly issued ParkingPermit
     * @throws IllegalArgumentException if the customer is not registered
     */
    public ParkingPermit register(Customer customer, Car car) {
        if (!listOfCustomers.contains(customer)) {
            throw new IllegalArgumentException(
                "Customer " + customer.getCustomerId() + " is not registered with this office.");
        }
        customer.addCar(car);
        return permitManager.issuePermit(car, customer);
    }

    // ------------------------------------------------------------ parking
    /**
     * Records a parking event for the given permit at the specified lot.
     *
     * @param date   the date of the parking event
     * @param permit the permit used
     * @param lot    the lot where the car is parked
     * @return the resulting ParkingTransaction
     */
    public ParkingTransaction park(Date date, ParkingPermit permit, ParkingLot lot) {
        return transactionManager.park(date, permit, lot);
    }

    // -------------------------------------------------------- charge queries
    /**
     * Returns total parking charges for a specific permit.
     */
    public Money getParkingCharges(ParkingPermit permit) {
        return transactionManager.getParkingCharges(permit);
    }

    /**
     * Returns total parking charges across all permits for a customer.
     */
    public Money getParkingCharges(Customer customer) {
        return transactionManager.getParkingCharges(customer);
    }

    // --------------------------------------------------- convenience accessors
    public PermitManager getPermitManager() {
        return permitManager;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }
}
