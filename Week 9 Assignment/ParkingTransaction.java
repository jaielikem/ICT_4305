package parking;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Records a single parking event (transaction) for a permit in a specific lot.
 * The charge is computed based on lot type, car type, and duration.
 */
public class ParkingTransaction {

    private final String transactionId;
    private final ParkingPermit permit;
    private final ParkingLot lot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double charge;

    public ParkingTransaction(String transactionId, ParkingPermit permit, ParkingLot lot, LocalDateTime entryTime) {
        this.transactionId = transactionId;
        this.permit = permit;
        this.lot = lot;
        this.entryTime = entryTime;
        this.charge = 0.0;

        // For ENTRY_ONLY lots, charge is applied immediately on entry
        if (lot.getScanMode() == ParkingLot.ScanMode.ENTRY_ONLY) {
            this.charge = lot.calculateCharge(permit.getCar().getCarType(), 0, 0);
        }
    }

    /**
     * Records the exit and finalises the charge for ENTRY_EXIT lots.
     * Also adds overnight charges for ENTRY_ONLY lots if applicable.
     *
     * @param exitTime the time the car exited the lot
     */
    public void recordExit(LocalDateTime exitTime) {
        this.exitTime = exitTime;

        long minutes = java.time.Duration.between(entryTime, exitTime).toMinutes();
        double hours = minutes / 60.0;
        int overnightDays = (int) (java.time.Duration.between(entryTime, exitTime).toDays());

        if (lot.getScanMode() == ParkingLot.ScanMode.ENTRY_EXIT) {
            this.charge = lot.calculateCharge(permit.getCar().getCarType(), hours, 0);
        } else {
            // ENTRY_ONLY: add overnight daily charges on top of entry charge
            if (overnightDays > 0) {
                this.charge += lot.calculateCharge(permit.getCar().getCarType(), 0, overnightDays);
            }
        }
    }

    public String getTransactionId() { return transactionId; }
    public ParkingPermit getPermit()  { return permit; }
    public ParkingLot getLot()        { return lot; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime()  { return exitTime; }
    public double getCharge()           { return charge; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingTransaction)) return false;
        ParkingTransaction that = (ParkingTransaction) o;
        return Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }

    @Override
    public String toString() {
        return "ParkingTransaction{id='" + transactionId
                + "', permit=" + permit.getPermitId()
                + ", charge=" + charge + "}";
    }
}
