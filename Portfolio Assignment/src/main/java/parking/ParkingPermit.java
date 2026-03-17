package parking;

import java.util.Date;

/**
 * A permit that authorises a specific car to park.
 */
public class ParkingPermit {
    private static int nextId = 5000;

    private final String permitId;
    private final Car car;
    private final Customer customer;
    private final Date issueDate;
    private Date expirationDate;

    public ParkingPermit(Car car, Customer customer, Date issueDate, Date expirationDate) {
        this.permitId       = "PERMIT-" + nextId++;
        this.car            = car;
        this.customer       = customer;
        this.issueDate      = issueDate;
        this.expirationDate = expirationDate;
    }

    public String getPermitId()       { return permitId; }
    public Car getCar()               { return car; }
    public Customer getCustomer()     { return customer; }
    public Date getIssueDate()        { return new Date(issueDate.getTime()); }
    public Date getExpirationDate()   { return new Date(expirationDate.getTime()); }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "ParkingPermit[id=" + permitId + ", plate=" + car.getLicensePlate() + "]";
    }
}
