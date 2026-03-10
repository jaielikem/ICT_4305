package parking;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The Parking Office manages customers, permits, lots, and transactions.
 *
 * Design notes (loose coupling):
 *  - Returns Collections of IDs (Strings) rather than exposing internal objects,
 *    so callers don't depend on internal data structures.
 *  - Overloaded getPermitIds() provides a focused view per customer without
 *    callers needing to know how permits are stored.
 */
public class ParkingOffice {

    private final String officeName;

    // Internal stores — kept private to encapsulate implementation details
    private final Map<String, Customer>         customers    = new LinkedHashMap<>();
    private final Map<String, ParkingPermit>    permits      = new LinkedHashMap<>();
    private final Map<String, ParkingLot>       lots         = new LinkedHashMap<>();
    private final List<ParkingTransaction>       transactions = new ArrayList<>();

    private int permitCounter = 1000;
    private int transactionCounter = 1;

    public ParkingOffice(String officeName) {
        this.officeName = officeName;
    }

    // -------------------------------------------------------------------------
    // Customer management
    // -------------------------------------------------------------------------

    /** Registers a new customer with the parking office. */
    public Customer addCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        return customer;
    }

    /** Returns the customer with the given ID, or null if not found. */
    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }

    /**
     * Returns a collection of all registered customer IDs.
     * Callers receive identifiers only — not direct access to Customer objects.
     */
    public Collection<String> getCustomerIds() {
        return Collections.unmodifiableCollection(customers.keySet());
    }

    // -------------------------------------------------------------------------
    // Permit management
    // -------------------------------------------------------------------------

    /**
     * Issues a new parking permit for a customer's car.
     * Auto-generates a unique permit ID.
     */
    public ParkingPermit addPermit(Customer customer, Car car) {
        String permitId = "PERMIT-" + (permitCounter++);
        ParkingPermit permit = new ParkingPermit(permitId, customer, car);
        permits.put(permitId, permit);
        return permit;
    }

    /** Returns the permit with the given ID, or null if not found. */
    public ParkingPermit getPermit(String permitId) {
        return permits.get(permitId);
    }

    /**
     * Returns a collection of ALL permit IDs currently issued.
     */
    public Collection<String> getPermitIds() {
        return Collections.unmodifiableCollection(permits.keySet());
    }

    /**
     * Returns a collection of permit IDs belonging to a specific customer.
     * Provides a customer-scoped view without exposing the full permit map.
     *
     * @param customer the customer whose permits are requested
     * @return collection of permit IDs for that customer
     */
    public Collection<String> getPermitIds(Customer customer) {
        return permits.values().stream()
                .filter(p -> p.getCustomer().equals(customer))
                .map(ParkingPermit::getPermitId)
                .collect(Collectors.toUnmodifiableList());
    }

    // -------------------------------------------------------------------------
    // Parking lot management
    // -------------------------------------------------------------------------

    /** Registers a parking lot with the office. */
    public ParkingLot addLot(ParkingLot lot) {
        lots.put(lot.getLotId(), lot);
        return lot;
    }

    /** Returns the parking lot with the given ID, or null if not found. */
    public ParkingLot getLot(String lotId) {
        return lots.get(lotId);
    }

    /** Returns all registered parking lots. */
    public Collection<ParkingLot> getLots() {
        return Collections.unmodifiableCollection(lots.values());
    }

    // -------------------------------------------------------------------------
    // Transaction management
    // -------------------------------------------------------------------------

    /**
     * Records a car entering a parking lot.
     * For ENTRY_ONLY lots the charge is applied immediately.
     *
     * @param permit    the permit being scanned
     * @param lot       the lot being entered
     * @param entryTime the timestamp of entry
     * @return the new ParkingTransaction
     */
    public ParkingTransaction park(ParkingPermit permit, ParkingLot lot, LocalDateTime entryTime) {
        String txId = "TX-" + (transactionCounter++);
        ParkingTransaction tx = new ParkingTransaction(txId, permit, lot, entryTime);
        transactions.add(tx);
        return tx;
    }

    /**
     * Records a car exiting a parking lot and finalises the charge.
     * For ENTRY_EXIT lots the total charge is calculated here.
     *
     * @param transaction the open transaction to close
     * @param exitTime    the timestamp of exit
     */
    public void exit(ParkingTransaction transaction, LocalDateTime exitTime) {
        transaction.recordExit(exitTime);
    }

    /**
     * Returns the total charges billed to a customer across all transactions
     * (used for monthly billing).
     */
    public double getTotalCharges(Customer customer) {
        return transactions.stream()
                .filter(tx -> tx.getPermit().getCustomer().equals(customer))
                .mapToDouble(ParkingTransaction::getCharge)
                .sum();
    }

    /** Returns all transactions (e.g. for reporting). */
    public List<ParkingTransaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public String getOfficeName() {
        return officeName;
    }
}
