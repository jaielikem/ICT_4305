# Assignment Reflection

## My Experience with Java Generics and Collections

This assignment provided valuable hands-on experience with Java generics and collections. I found the basic implementation of the Month class straightforward once I understood that the `<T>` syntax enables type flexibility while maintaining type safety. Completing the getter and setter methods required only fundamental Java knowledge.

The most challenging aspect was implementing the HashMap approach. I initially struggled to understand how custom generic objects function as keys in a HashMap. Through research, I discovered that without proper `equals()` and `hashCode()` methods, HashMap lookups using new Month objects wouldn't work correctly. This was a significant learning moment that deepened my understanding beyond what I expected from the assignment.

Working with Arrays and ArrayLists felt more intuitive since I had prior exposure to these structures. The parallel implementation of separate collections for month numbers and names reinforced my understanding of type-specific collections.

I wish I had known earlier about the limitations of generic array creation in Java and why the `@SuppressWarnings("unchecked")` annotation is necessary. I spent unnecessary time trying to eliminate warnings before learning this is an accepted practice.

For my implementation decisions, I created separate demonstration methods for each collection type to improve code readability and organization. I also defined a single String array for month names to avoid duplication across all three approaches, following the DRY (Don't Repeat Yourself) principle.

This assignment successfully strengthened my understanding of generics, collections, and the trade-offs between different data structures in Java.

