package parking;

/**
 * Immutable value class representing a monetary amount stored as cents.
 */
public class Money {
    private final long cents;

    public Money(long cents) {
        this.cents = cents;
    }

    /** Convenience factory — pass dollars as a double, e.g. Money.of(2.50) */
    public static Money of(double dollars) {
        return new Money(Math.round(dollars * 100));
    }

    public long getCents() { return cents; }

    public double getDollars() { return cents / 100.0; }

    /** Returns a new Money that is the sum of this and other. */
    public Money add(Money other) {
        return new Money(this.cents + other.cents);
    }

    @Override
    public String toString() {
        return String.format("$%.2f", getDollars());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        return cents == ((Money) o).cents;
    }

    @Override
    public int hashCode() { return Long.hashCode(cents); }
}
