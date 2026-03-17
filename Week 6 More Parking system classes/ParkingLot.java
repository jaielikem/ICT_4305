package parking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a parking lot. Uses Java Generics via typed List collections.
 */
public class ParkingLot {
    private final String lotId;
    private final String name;
    private final Address address;
    private final int capacity;
    private final Money hourlyRate;

    // Generic list keeps track of permits currently parked in this lot
    private final List<ParkingPermit> parkedPermits;

    public ParkingLot(String lotId, String name, Address address,
                      int capacity, Money hourlyRate) {
        this.lotId        = lotId;
        this.name         = name;
        this.address      = address;
        this.capacity     = capacity;
        this.hourlyRate   = hourlyRate;
        this.parkedPermits = new ArrayList<>();
    }

    public String getLotId()       { return lotId; }
    public String getName()        { return name; }
    public Address getAddress()    { return address; }
    public int getCapacity()       { return capacity; }
    public Money getHourlyRate()   { return hourlyRate; }

    public List<ParkingPermit> getParkedPermits() {
        return Collections.unmodifiableList(parkedPermits);
    }

    public boolean isFull() { return parkedPermits.size() >= capacity; }

    /**
     * Records a permit as entering this lot.
     * @return the ParkingCharge incurred, or null if lot is full.
     */
    public ParkingCharge enter(ParkingPermit permit) {
        if (isFull()) return null;
        parkedPermits.add(permit);
        return new ParkingCharge(permit.getPermitId(), lotId, hourlyRate);
    }

    /**
     * Removes the permit when the car exits.
     */
    public boolean exit(ParkingPermit permit) {
        return parkedPermits.remove(permit);
    }

    @Override
    public String toString() {
        return "ParkingLot{id='" + lotId + "', name='" + name +
               "', capacity=" + capacity + ", rate=" + hourlyRate + "}";
    }
}
