package parking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Manages parking transactions.
 * Responsible for recording park events and computing aggregate charges.
 */
public class TransactionManager {
    private final List<ParkingTransaction> transactions = new ArrayList<>();

    /**
     * Records a new parking transaction and returns it.
     */
    public ParkingTransaction park(Date date, ParkingPermit permit, ParkingLot lot) {
        Money charge = lot.calculateCharge(permit.getCar().getCarType());
        ParkingTransaction txn = new ParkingTransaction(date, permit, lot, charge);
        transactions.add(txn);
        return txn;
    }

    /**
     * Returns the total parking charges accrued for a specific permit.
     */
    public Money getParkingCharges(ParkingPermit permit) {
        Money total = new Money(0.0);
        for (ParkingTransaction txn : transactions) {
            if (txn.getPermit().getPermitId().equals(permit.getPermitId())) {
                total = total.add(txn.getCharge());
            }
        }
        return total;
    }

    /**
     * Returns the total parking charges accrued for all permits belonging to a customer.
     */
    public Money getParkingCharges(Customer customer) {
        Money total = new Money(0.0);
        for (ParkingTransaction txn : transactions) {
            if (txn.getPermit().getCustomer().getCustomerId()
                    .equals(customer.getCustomerId())) {
                total = total.add(txn.getCharge());
            }
        }
        return total;
    }

    /**
     * Returns all transactions for a given customer.
     */
    public List<ParkingTransaction> getTransactionsForCustomer(Customer customer) {
        List<ParkingTransaction> result = new ArrayList<>();
        for (ParkingTransaction txn : transactions) {
            if (txn.getPermit().getCustomer().getCustomerId()
                    .equals(customer.getCustomerId())) {
                result.add(txn);
            }
        }
        return Collections.unmodifiableList(result);
    }

    public List<ParkingTransaction> getAllTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}
