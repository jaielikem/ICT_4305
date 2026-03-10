package parking;

import java.util.Objects;

/**
 * Represents a university parking lot.
 * Each lot has its own hourly rate and scan mode:
 *   - ENTRY_ONLY: charges on entry plus overnight daily rate
 *   - ENTRY_EXIT: charges based on total hours parked upon exit
 */
public class ParkingLot {

    public enum ScanMode {
        ENTRY_ONLY,
        ENTRY_EXIT
    }

    private final String lotId;
    private final String name;
    private final double hourlyRate;     // base rate per hour (SUV)
    private final double dailyRate;      // overnight charge (ENTRY_ONLY lots)
    private final ScanMode scanMode;

    public ParkingLot(String lotId, String name, double hourlyRate, double dailyRate, ScanMode scanMode) {
        this.lotId = lotId;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.dailyRate = dailyRate;
        this.scanMode = scanMode;
    }

    public String getLotId() {
        return lotId;
    }

    public String getName() {
        return name;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public ScanMode getScanMode() {
        return scanMode;
    }

    /**
     * Calculates a charge for a given car type and hours parked.
     * Compact cars receive a 20% discount versus SUV rate.
     *
     * @param carType  the type of car (SUV or COMPACT)
     * @param hours    total hours parked (used for ENTRY_EXIT lots)
     * @param days     overnight days (used for ENTRY_ONLY lots)
     * @return the total charge
     */
    public double calculateCharge(CarType carType, double hours, int days) {
        double baseCharge;
        if (scanMode == ScanMode.ENTRY_EXIT) {
            baseCharge = hourlyRate * hours;
        } else {
            // ENTRY_ONLY: flat entry charge (one hour equivalent) + daily overnight
            baseCharge = hourlyRate + (dailyRate * days);
        }
        if (carType == CarType.COMPACT) {
            baseCharge *= 0.80; // 20% discount for compact cars
        }
        return baseCharge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingLot)) return false;
        ParkingLot lot = (ParkingLot) o;
        return Objects.equals(lotId, lot.lotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lotId);
    }

    @Override
    public String toString() {
        return "ParkingLot{lotId='" + lotId + "', name='" + name + "', scanMode=" + scanMode + "}";
    }
}
