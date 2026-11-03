import java.io.*;
import java.util.*;

/**
 * Assignment 3: Object Serialization and Deserialization.
 * Writes multiple Employee objects with ObjectOutputStream and
 * reads them back with ObjectInputStream.
 */
public class EmployeeSerializationDemo {

    static final String FILE_NAME = "employees.ser";

    public static void serialize(List<Employee> employees) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(FILE_NAME)))) {
            for (Employee e : employees) {
                oos.writeObject(e);
            }
            System.out.println(employees.size() + " employee objects serialized to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Serialization error: " + e.getMessage());
        }
    }

    public static List<Employee> deserialize() {
        List<Employee> list = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(FILE_NAME)))) {
            while (true) {
                list.add((Employee) ois.readObject());
            }
        } catch (EOFException e) {
            // normal end of file reached
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Deserialization error: " + e.getMessage());
        }
        return list;
    }

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Amit Kumar", "IT", 55000));
        employees.add(new Employee(2, "Simran Kaur", "HR", 48000));
        employees.add(new Employee(3, "Rohit Mehta", "Finance", 62000.50));
        employees.add(new Employee(4, "Anjali Gupta", "IT", 71000));

        serialize(employees);

        List<Employee> loaded = deserialize();
        System.out.println("\n---- Employees read from file ----");
        System.out.printf("%-5s %-15s %-12s %10s%n", "ID", "Name", "Department", "Salary");
        for (Employee e : loaded) System.out.println(e);
        System.out.println("\nTotal employees retrieved: " + loaded.size());
    }
}
