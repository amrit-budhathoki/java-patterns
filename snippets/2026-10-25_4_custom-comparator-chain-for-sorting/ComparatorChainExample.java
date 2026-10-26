// Title: Custom Comparator Chain for Sorting

// Demonstrates chaining multiple comparators together to sort objects by multiple criteria.
// Useful when you need flexible, multi-level sorting without modifying your data class
// or when sorting rules change frequently at runtime.

import java.util.*;

record Person(String name, int age, double salary) {}

public class ComparatorChainExample {
    public static void main(String[] args) {
        List<Person> people = List.of(
            new Person("Alice", 30, 75000),
            new Person("Bob", 25, 80000),
            new Person("Charlie", 30, 70000),
            new Person("Diana", 25, 85000)
        );

        System.out.println("Original order:");
        people.forEach(System.out::println);

        // Build a comparator chain: first by age, then by salary, then by name
        Comparator<Person> chain = Comparator
            .comparingInt(Person::age)
            .thenComparingDouble(Person::salary)
            .thenComparing(Person::name);

        List<Person> sorted = people.stream()
            .sorted(chain)
            .toList();

        System.out.println("\nSorted by age, then salary, then name:");
        sorted.forEach(System.out::println);

        // Reverse the entire chain
        System.out.println("\nReversed chain:");
        people.stream()
            .sorted(chain.reversed())
            .forEach(System.out::println);

        // Custom chain: by salary descending, then by name ascending
        Comparator<Person> customChain = Comparator
            .comparingDouble(Person::salary)
            .reversed()
            .thenComparing(Person::name);

        System.out.println("\nBy salary (descending), then name:");
        people.stream()
            .sorted(customChain)
            .forEach(System.out::println);
    }
}
