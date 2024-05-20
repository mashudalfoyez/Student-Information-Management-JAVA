import java.io.*;
import java.util.*;

class Student {
    private final String studentID;
    private String name;
    private int age;
    private String gender;

    public Student(String studentID, String name, int age, String gender) {
        this.studentID = studentID;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return studentID + "," + name + "," + age + "," + gender;
    }
}

class StudentManagementSystem {
    private final List<Student> students = new ArrayList<>();
    private final String FILE_NAME = "C:\\Users\\mashu\\OneDrive\\Desktop\\CSI-2300-Java\\students.txt";
    private final Scanner scanner = new Scanner(System.in);

    public StudentManagementSystem() {
        loadStudentsFromFile();
    }

    private void loadStudentsFromFile() {
        try (Scanner fileScanner = new Scanner(new FileReader(FILE_NAME))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                students.add(new Student(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private void saveStudentsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student student : students) {
                writer.println(student);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void showAllStudents() {
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void insertStudent() {
        System.out.println("Please input the student ID");
        String studentID = scanner.nextLine();
        System.out.println("Please input the name of the student");
        String name = scanner.nextLine();
        System.out.println("Please input the age of the student");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.println("Please input the gender of the student");
        String gender = scanner.nextLine();
        students.add(new Student(studentID, name, age, gender));
        System.out.println("Student added successfully.");
    }

    public void deleteStudent() {
        System.out.println("Please input the student ID of the student you want to delete");
        String studentID = scanner.nextLine();
        students.removeIf(student -> student.getStudentID().equals(studentID));
        System.out.println("The student with student ID [" + studentID + "] has been deleted successfully.");
    }

    public void updateStudent() {
        System.out.println("Please input the student ID of the student you want to update");
        String studentID = scanner.nextLine();
        Optional<Student> optionalStudent = students.stream().filter(student -> student.getStudentID().equals(studentID)).findFirst();
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            System.out.println("Please input the new name (input 0 to skip)");
            String newName = scanner.nextLine();
            if (!newName.equals("0")) {
                student.setName(newName);
            }
            System.out.println("Please input the new age (input 0 to skip)");
            String newAgeString = scanner.nextLine();
            if (!newAgeString.equals("0")) {
                int newAge = Integer.parseInt(newAgeString);
                student.setAge(newAge);
            }
            System.out.println("Please input the new gender (input 0 to skip)");
            String newGender = scanner.nextLine();
            if (!newGender.equals("0")) {
                student.setGender(newGender);
            }
            System.out.println("The student with student ID [" + studentID + "] has been updated successfully.");
        } else {
            System.out.println("Student with ID [" + studentID + "] not found.");
        }
    }

    public void findStudent() {
        System.out.println("Please input the student ID of the student you want to find");
        String studentID = scanner.nextLine();
        Optional<Student> optionalStudent = students.stream().filter(student -> student.getStudentID().equals(studentID)).findFirst();
        if (optionalStudent.isPresent()) {
            System.out.println(optionalStudent.get());
        } else {
            System.out.println("Student with ID [" + studentID + "] not found.");
        }
    }

    public void rankStudents() {
        System.out.println("Please input 1(else) to rank the students in descending(ascending) order according to their student IDs");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            students.sort(Comparator.comparing(Student::getStudentID).reversed());
        } else {
            students.sort(Comparator.comparing(Student::getStudentID));
        }
        showAllStudents();
    }

    public void findMinMaxStudent() {
        System.out.println("Please input 1(else) to find the students with max(min) student ID");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            Optional<Student> maxStudent = students.stream().max(Comparator.comparingInt(student -> Integer.parseInt(student.getStudentID())));
            maxStudent.ifPresent(System.out::println);
        } else {
            Optional<Student> minStudent = students.stream().min(Comparator.comparingInt(student -> Integer.parseInt(student.getStudentID())));
            minStudent.ifPresent(System.out::println);
        }
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("*****************************************************************");
            System.out.println("*\tStudent Management System\t\t\t\t*");
            System.out.println("*\tAuthor Weifeng Pan\t\t\t\t\t*");
            System.out.println("*\t03-17-2024\t\t\t\t\t\t*");
            System.out.println("*\t[0] input 0 to save the information and exit the program*");
            System.out.println("*\t[1] input 1 to show all the students in the class\t*");
            System.out.println("*\t[2] input 2 to insert several students to the class\t*");
            System.out.println("*\t[3] input 3 to delete a student from the class\t\t*");
            System.out.println("*\t[4] input 4 to update the information of a student\t*");
            System.out.println("*\t[5] input 5 to find the information of a student\t*");
            System.out.println("*\t[6] input 6 to rank the students with regard to stuID\t*");
            System.out.println("*\t[7] input 7 to find the students with max/min stuID\t*");
            System.out.println("*****************************************************************");
            System.out.println("Please input your choice:");
            String choice = scanner.nextLine();
            switch (choice) {
                case "0":
                    saveStudentsToFile();
                    running = false;
                    break;
                case "1":
                    showAllStudents();
                    break;
                case "2":
                    insertStudent();
                    break;
                case "3":
                    deleteStudent();
                    break;
                case "4":
                    updateStudent();
                    break;
                case "5":
                    findStudent();
                    break;
                case "6":
                    rankStudents();
                    break;
                case "7":
                    findMinMaxStudent();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        system.run();
    }
}

