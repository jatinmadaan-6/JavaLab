import java.io.*;

/**
 * Assignment 2: File-Based Log Analyzer.
 * Reads a log with BufferedReader, counts entries, finds ERROR / WARNING lines,
 * and writes filtered entries and a summary using BufferedWriter.
 */
public class LogAnalyzer {

    public static void main(String[] args) {
        String logFile = args.length > 0 ? args[0] : "application.log";
        String filteredFile = "filtered_logs.txt";
        String summaryFile = "log_summary.txt";

        if (args.length == 0) createSampleLog(logFile);

        int total = 0, errors = 0, warnings = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile));
             BufferedWriter filtered = new BufferedWriter(new FileWriter(filteredFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // ignore blank lines
                total++;
                String upper = line.toUpperCase();
                boolean isError = upper.contains("ERROR");
                boolean isWarning = upper.contains("WARNING");
                if (isError) errors++;
                if (isWarning) warnings++;
                if (isError || isWarning) {
                    filtered.write(line);
                    filtered.newLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Log file not found: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            return;
        }

        try (BufferedWriter summary = new BufferedWriter(new FileWriter(summaryFile))) {
            summary.write("LOG ANALYSIS SUMMARY"); summary.newLine();
            summary.write("--------------------"); summary.newLine();
            summary.write("Source file          : " + logFile); summary.newLine();
            summary.write("Total log entries    : " + total); summary.newLine();
            summary.write("ERROR entries        : " + errors); summary.newLine();
            summary.write("WARNING entries      : " + warnings); summary.newLine();
            summary.write("Filtered entries file: " + filteredFile); summary.newLine();
        } catch (IOException e) {
            System.err.println("Could not write summary: " + e.getMessage());
            return;
        }

        System.out.println("Total log entries : " + total);
        System.out.println("ERROR entries     : " + errors);
        System.out.println("WARNING entries   : " + warnings);
        System.out.println("Filtered lines saved to '" + filteredFile + "', summary in '" + summaryFile + "'.");
    }

    private static void createSampleLog(String fileName) {
        String[] lines = {
            "2026-11-03 09:00:01 INFO  Application started",
            "2026-11-03 09:00:05 INFO  Connected to database",
            "2026-11-03 09:01:12 WARNING Disk usage at 85%",
            "2026-11-03 09:02:30 INFO  User 'admin' logged in",
            "2026-11-03 09:03:44 ERROR Failed to load configuration file",
            "2026-11-03 09:04:10 INFO  Request processed in 120ms",
            "2026-11-03 09:05:22 WARNING Slow response from payment service",
            "2026-11-03 09:06:01 ERROR NullPointerException in OrderService",
            "2026-11-03 09:07:15 INFO  Cache refreshed",
            "2026-11-03 09:08:00 INFO  Application shutdown"
        };
        try (BufferedWriter w = new BufferedWriter(new FileWriter(fileName))) {
            for (String l : lines) { w.write(l); w.newLine(); }
            System.out.println("Sample log created: " + fileName);
        } catch (IOException e) {
            System.err.println("Could not create sample log: " + e.getMessage());
        }
    }
}
