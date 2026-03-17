package parking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Manages the lifecycle of parking permits.
 * Responsible for issuing new permits and retrieving existing ones.
 */
public class PermitManager {
    private final List<ParkingPermit> permits = new ArrayList<>();

    /**
     * Issues a new one-year permit for the given car and customer.
     */
    public ParkingPermit issuePermit(Car car, Customer customer) {
        Date issueDate      = new Date();
        Date expirationDate = new Date(issueDate.getTime() + 365L * 24 * 60 * 60 * 1000);
        ParkingPermit permit = new ParkingPermit(car, customer, issueDate, expirationDate);
        permits.add(permit);
        return permit;
    }

    /**
     * Returns all permits belonging to the specified customer.
     */
    public List<ParkingPermit> getPermitsForCustomer(Customer customer) {
        List<ParkingPermit> result = new ArrayList<>();
        for (ParkingPermit p : permits) {
            if (p.getCustomer().getCustomerId().equals(customer.getCustomerId())) {
                result.add(p);
            }
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Finds a permit by its ID.
     */
    public Optional<ParkingPermit> findPermitById(String permitId) {
        return permits.stream()
                      .filter(p -> p.getPermitId().equals(permitId))
                      .findFirst();
    }

    public List<ParkingPermit> getAllPermits() {
        return Collections.unmodifiableList(permits);
    }
}
