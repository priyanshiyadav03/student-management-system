import java.io.*;
import java.util.Collections;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

record Student(String name, int roll_no, String grade) {
    @Override
    public String toString() {
        return "Name: " + name + ", Roll no: " + roll_no + ", Grade: " + grade;
    }
}

class StudentManagementSystem {

    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(int roll_no) {
        students.removeIf(student -> student.roll_no() == roll_no);
    }

    public Student findStudent(int roll_no) {
        for (Student student : students) {
            if (student.roll_no() == roll_no) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public void storeToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            students = Collections.unmodifiableList((List<Student>) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

public class Task5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();

        while (true) {
            System.out.println("Student Management System");
            System.out.println("Enter 1 to add student ");
            System.out.println("Enter 2 to remove student ");
            System.out.println("Enter 3 to search student ");
            System.out.println("Enter 4 to display all students");
            System.out.println("Enter 5 to store in file");
            System.out.println("Enter 6 to read from file");
            System.out.println("Enter 7 to exit");
            System.out.println("Choose an option");

            int num = sc.nextInt();
            sc.nextLine();

            switch (num) {
                case 1 -> {
                    System.out.println("Enter name of student.");
                    String name = sc.nextLine();

                    System.out.println("Enter roll number.");
                    int roll_no = sc.nextInt();
                    sc.nextLine(); // Consume the leftover newline

                    System.out.println("Enter grade.");
                    String grade = sc.nextLine();

                    Student student = new Student(name, roll_no, grade);
                    sms.addStudent(student);
                }
                case 2 -> {
                    System.out.println("Enter roll number to remove the student.");
                    int roll_num = sc.nextInt();
                    sc.nextLine();

                    sms.removeStudent(roll_num);
                }
                case 3 -> {
                    System.out.println("Enter roll number to search student.");
                    int roll_no = sc.nextInt();
                    sc.nextLine();

                    Student foundStudent = sms.findStudent(roll_no);

                    if (foundStudent != null) {
                        System.out.println("Student found: " + foundStudent);
                    } else {
                        System.out.println("Student not found.");
                    }
                }
                case 4 -> {
                    List<Student> allStudents = sms.getAllStudents();

                    if (allStudents.isEmpty()) {
                        System.out.println("No students found in the system.");
                    } else {
                        for (Student stu : allStudents) {
                            System.out.println(stu);
                        }
                    }
                }
                case 5 -> {
                    sms.storeToFile("students.data");
                    System.out.println("Data saved to file.");
                }
                case 6 -> {
                    sms.readFromFile("students.data");
                    System.out.println("Data loaded from file.");
                }
                case 7 -> {
                    System.out.println("Exiting application.");
                    sc.close();
                    System.exit(0);
                }
                default -> System.out.println("Option not found, please try again.");
            }
        }
    }
}
