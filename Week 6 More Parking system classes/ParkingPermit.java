package parking;

import java.time.Instant;
import java.util.UUID;

/**
 * A parking permit issued to a registered car.
 */
public class ParkingPermit {
    private final String permitId;
    private final Car car;
    private final Instant issued;

    public ParkingPermit(Car car) {
        this.permitId = UUID.randomUUID().toString();
        this.car      = car;
        this.issued   = Instant.now();
    }

    public String getPermitId() { return permitId; }
    public Car getCar()         { return car; }
    public Instant getIssued()  { return issued; }

    @Override
    public String toString() {
        return "ParkingPermit{id='" + permitId + "', car=" + car + ", issued=" + issued + "}";
    }
}
