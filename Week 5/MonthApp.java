import java.util.ArrayList;
import java.util.HashMap;

/**
 * MonthApp demonstrates the use of Java generics and collections
 * with the Month class using Arrays, ArrayLists, and HashMaps
 */
public class MonthApp {
    
    public static void main(String[] args) {
        // Month names for reference
        String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        
        System.out.println("=== APPROACH 1: Using Arrays ===\n");
        demonstrateArrayApproach(monthNames);
        
        System.out.println("\n=== APPROACH 2: Using ArrayLists ===\n");
        demonstrateArrayListApproach(monthNames);
        
        System.out.println("\n=== APPROACH 3: Using HashMap ===\n");
        demonstrateHashMapApproach(monthNames);
    }
    
    /**
     * Approach 1: Using Arrays to store Month objects
     */
    @SuppressWarnings("unchecked")
    private static void demonstrateArrayApproach(String[] monthNames) {
        // Define arrays that can hold Month objects
        Month<Integer>[] monthNumberArray = new Month[12];  // ignore warning
        Month<String>[] monthNameArray = new Month[12];     // ignore warning
        
        // Populate the arrays
        for (int i = 0; i < 12; i++) {
            // Create and populate month number
            Month<Integer> monthNumber = new Month<>(i + 1);
            monthNumberArray[i] = monthNumber;
            
            // Create and populate month name
            Month<String> monthName = new Month<>(monthNames[i]);
            monthNameArray[i] = monthName;
        }
        
        // Display the results
        System.out.println("Month Index and Names (Array):");
        for (int i = 0; i < 12; i++) {
            System.out.println(monthNumberArray[i].getMonth() + " = " + 
                             monthNameArray[i].getMonth());
        }
    }
    
    /**
     * Approach 2: Using ArrayLists to store Month objects
     */
    private static void demonstrateArrayListApproach(String[] monthNames) {
        // Create ArrayLists for Month objects
        ArrayList<Month<Integer>> monthNumberList = new ArrayList<>();
        ArrayList<Month<String>> monthNameList = new ArrayList<>();
        
        // Populate the ArrayLists
        for (int i = 0; i < 12; i++) {
            // Add month number
            monthNumberList.add(new Month<>(i + 1));
            
            // Add month name
            monthNameList.add(new Month<>(monthNames[i]));
        }
        
        // Display the results
        System.out.println("Month Index and Names (ArrayList):");
        for (int i = 0; i < monthNumberList.size(); i++) {
            System.out.println(monthNumberList.get(i).getMonth() + " = " + 
                             monthNameList.get(i).getMonth());
        }
    }
    
    /**
     * Approach 3: Using HashMap to store Month objects as key-value pairs
     */
    private static void demonstrateHashMapApproach(String[] monthNames) {
        // Create HashMap with Month<Integer> as key and Month<String> as value
        HashMap<Month<Integer>, Month<String>> monthMap = new HashMap<>();
        
        // Populate the HashMap
        for (int i = 0; i < 12; i++) {
            Month<Integer> monthNumber = new Month<>(i + 1);
            Month<String> monthName = new Month<>(monthNames[i]);
            monthMap.put(monthNumber, monthName);
        }
        
        // Display the results
        // Note: HashMap doesn't guarantee order, so we'll iterate through 1-12
        System.out.println("Month Index and Names (HashMap):");
        for (int i = 1; i <= 12; i++) {
            // Create temporary key to search
            Month<Integer> searchKey = new Month<>(i);
            
            // Find matching entry by iterating through the map
            for (Month<Integer> key : monthMap.keySet()) {
                if (key.getMonth().equals(i)) {
                    System.out.println(key.getMonth() + " = " + 
                                     monthMap.get(key).getMonth());
                    break;
                }
            }
        }
    }
}
