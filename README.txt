JAVA LAB ASSIGNMENT - COMPLETE SOLUTION
=======================================

q1/StudentFileManager.java
  Byte streams (FileInputStream/FileOutputStream) copy a binary file;
  character streams (FileReader/FileWriter) write, append and read student records.
  try/catch/finally, all streams closed.

q2/LogAnalyzer.java
  BufferedReader/BufferedWriter log analyzer: counts entries, finds ERROR and
  WARNING lines, saves them to filtered_logs.txt, writes log_summary.txt.
  Optional argument: path to your own log file (a sample log is auto-created if none given).

q3/Employee.java, EmployeeSerializationDemo.java
  Serializable Employee (id, name, department, salary) written with
  ObjectOutputStream and read back with ObjectInputStream.

HOW TO RUN (Java 8+ required)
-----------------------------
cd q1
javac StudentFileManager.java
java StudentFileManager

cd ../q2
javac LogAnalyzer.java
java LogAnalyzer            (or: java LogAnalyzer mylog.log)

cd ../q3
javac *.java
java EmployeeSerializationDemo
