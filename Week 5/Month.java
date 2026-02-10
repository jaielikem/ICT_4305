/**
 * Generic Month class that can accept Integer or String data type
 * @param <T> the type of data stored (Integer for month number or String for month name)
 */
public class Month<T> { 
    private T month; 

    // Getter method
    public T getMonth() {
        return month;
    }
    
    // Setter method
    public void setMonth(T month) {
        this.month = month;
    }

    // Default constructor
    public Month() { }
    
    // Parameterized constructor
    public Month(T month) {
        this.month = month;
    }
}
