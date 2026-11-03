import java.io.*;
import java.util.*;

/**
 * Assignment 1: File Handling Using Byte and Character Streams.
 *  - Byte streams  (FileInputStream / FileOutputStream): copy a binary file.
 *  - Character streams (FileReader / FileWriter): write and read student details.
 *  - Exception handling, and all streams closed in finally blocks.
 */
public class StudentFileManager {

    static final String STUDENT_FILE = "students.txt";
    static final String BINARY_SOURCE = "source.bin";
    static final String BINARY_COPY = "copy.bin";

    // ---------- Byte stream: copy a binary file ----------
    public static void copyBinaryFile(String src, String dest) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            byte[] buffer = new byte[4096];
            int bytesRead;
            long total = 0;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                total += bytesRead;
            }
            System.out.println("Binary file copied: " + src + " -> " + dest + " (" + total + " bytes)");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error while copying: " + e.getMessage());
        } finally {
            closeQuietly(fis);
            closeQuietly(fos);
        }
    }

    // ---------- Character stream: write student details ----------
    public static void writeStudents(String fileName, List<String[]> students) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName);
            for (String[] s : students) {
                fw.write(String.join(",", s) + System.lineSeparator());
            }
            System.out.println("Student details written to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing students: " + e.getMessage());
        } finally {
            closeQuietly(fw);
        }
    }

    // ---------- Character stream: append a student ----------
    public static void appendStudent(String fileName, String[] student) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName, true); // append mode
            fw.write(String.join(",", student) + System.lineSeparator());
            System.out.println("Appended student: " + student[1]);
        } catch (IOException e) {
            System.err.println("Error appending student: " + e.getMessage());
        } finally {
            closeQuietly(fw);
        }
    }

    // ---------- Character stream: read student details ----------
    public static void readStudents(String fileName) {
        FileReader fr = null;
        try {
            fr = new FileReader(fileName);
            StringBuilder content = new StringBuilder();
            int ch;
            while ((ch = fr.read()) != -1) {
                content.append((char) ch);
            }
            System.out.println("\n---- Student Records ----");
            System.out.printf("%-6s %-15s %-8s %-6s%n", "ID", "Name", "Course", "Marks");
            for (String line : content.toString().split("\\R")) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                System.out.printf("%-6s %-15s %-8s %-6s%n", p[0], p[1], p[2], p[3]);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading students: " + e.getMessage());
        } finally {
            closeQuietly(fr);
        }
    }

    // Creates a small binary file so the copy demo works out of the box
    private static void createSampleBinary(String fileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            for (int i = 0; i < 1000; i++) fos.write(i % 256);
        } catch (IOException e) {
            System.err.println("Could not create sample binary: " + e.getMessage());
        } finally {
            closeQuietly(fos);
        }
    }

    // Compares two files byte-by-byte
    private static boolean filesIdentical(String a, String b) {
        FileInputStream f1 = null, f2 = null;
        try {
            f1 = new FileInputStream(a);
            f2 = new FileInputStream(b);
            int x, y;
            do {
                x = f1.read();
                y = f2.read();
                if (x != y) return false;
            } while (x != -1);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            closeQuietly(f1);
            closeQuietly(f2);
        }
    }

    private static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                System.err.println("Error closing stream: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Part 1: byte streams
        createSampleBinary(BINARY_SOURCE);
        copyBinaryFile(BINARY_SOURCE, BINARY_COPY);
        System.out.println("Copy identical to source? " + filesIdentical(BINARY_SOURCE, BINARY_COPY));

        // Part 2: character streams
        List<String[]> students = new ArrayList<>();
        students.add(new String[]{"101", "Aman Singh", "BCA", "85"});
        students.add(new String[]{"102", "Priya Sharma", "BCA", "92"});
        students.add(new String[]{"103", "Rahul Verma", "MCA", "78"});
        writeStudents(STUDENT_FILE, students);
        appendStudent(STUDENT_FILE, new String[]{"104", "Neha Kaur", "MCA", "88"});
        readStudents(STUDENT_FILE);

        // Exception handling demo: missing file
        System.out.println("\nTrying to read a missing file:");
        readStudents("does_not_exist.txt");
    }
}
