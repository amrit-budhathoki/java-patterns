// Title: Comparator Composition with Multiple Sort Keys

// Demonstrates chaining multiple comparators to sort objects by multiple criteria
// in sequence. Use this when you need complex sorting logic with fallback criteria,
// like sorting employees by department, then by salary, then by name.

import java.util.*;

record Employee(String name, String department, int salary) {}

public class ComparatorChain {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("Alice Johnson", "Engineering", 95000),
            new Employee("Bob Smith", "Engineering", 88000),
            new Employee("Charlie Brown", "Sales", 75000),
            new Employee("Diana Prince", "Sales", 78000),
            new Employee("Eve Wilson", "Engineering", 88000),
            new Employee("Frank Miller", "HR", 65000)
        );

        System.out.println("Original list:");
        employees.forEach(System.out::println);

        // Chain comparators: first by department, then by salary (descending), then by name
        Comparator<Employee> chainedComparator = Comparator
            .comparing(Employee::department)
            .thenComparingInt(Employee::salary).reversed()
            .thenComparing(Employee::name);

        employees.sort(chainedComparator);

        System.out.println("\nSorted by department, then salary (desc), then name:");
        employees.forEach(System.out::println);

        // Alternative: explicit comparator chain for custom logic
        Comparator<Employee> customChain = (e1, e2) -> {
            int deptCompare = e1.department().compareTo(e2.department());
            if (deptCompare != 0) return deptCompare;

            int salaryCompare = Integer.compare(e2.salary(), e1.salary());
            if (salaryCompare != 0) return salaryCompare;

            return e1.name().compareTo(e2.name());
        };

        employees.sort(customChain);
        System.out.println("\nUsing explicit chain (same result):");
        employees.forEach(System.out::println);
    }
}
