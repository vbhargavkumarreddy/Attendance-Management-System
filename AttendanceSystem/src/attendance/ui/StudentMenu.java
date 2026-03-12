package attendance.ui;

import attendance.dao.StudentDAO;
import attendance.model.Student;
import attendance.service.AuthService;

import java.util.List;

public class StudentMenu {
    private final StudentDAO studentDAO = new StudentDAO();
    private final AuthService auth = AuthService.getInstance();

    public void show() {
        while (true) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("STUDENT MANAGEMENT");
            System.out.println();
            System.out.println("  1. View All Students");
            System.out.println("  2. Add Student");
            System.out.println("  3. Update Student");
            System.out.println("  4. Delete Student");
            System.out.println("  5. Search Student");
            System.out.println("  0. Back");
            System.out.println();

            String choice = ConsoleUI.prompt("Select option");
            switch (choice) {
                case "1": viewAll(); break;
                case "2": if (auth.isAdmin()) addStudent(); else ConsoleUI.printError("Admin only."); break;
                case "3": if (auth.isAdmin()) updateStudent(); else ConsoleUI.printError("Admin only."); break;
                case "4": if (auth.isAdmin()) deleteStudent(); else ConsoleUI.printError("Admin only."); break;
                case "5": searchStudent(); break;
                case "0": return;
                default:  ConsoleUI.printError("Invalid option.");
            }
            if (!choice.equals("0")) ConsoleUI.pause();
        }
    }

    private void viewAll() {
        List<Student> students = studentDAO.getAllStudents();
        System.out.println();
        if (students.isEmpty()) { ConsoleUI.printInfo("No students found."); return; }
        ConsoleUI.printTableHeader("ID", "Name", "Course", "Email");
        for (Student s : students) {
            ConsoleUI.printTableRow(s.getId(), s.getName(), s.getCourse(), s.getEmail());
        }
        ConsoleUI.printTableFooter(4);
        System.out.println();
        ConsoleUI.printInfo("Total: " + students.size() + " student(s)");
    }

    private void addStudent() {
        System.out.println();
        ConsoleUI.printHeader("ADD STUDENT");
        String id     = ConsoleUI.prompt("Student ID");
        String name   = ConsoleUI.prompt("Full Name");
        String course = ConsoleUI.prompt("Course");
        String email  = ConsoleUI.prompt("Email");

        if (studentDAO.addStudent(new Student(id, name, course, email))) {
            ConsoleUI.printSuccess("Student added successfully.");
        } else {
            ConsoleUI.printError("Student ID already exists.");
        }
    }

    private void updateStudent() {
        System.out.println();
        String id = ConsoleUI.prompt("Enter Student ID to update");
        Student s = studentDAO.findById(id);
        if (s == null) { ConsoleUI.printError("Student not found."); return; }

        ConsoleUI.printInfo("Leave blank to keep current value.");
        String name   = ConsoleUI.prompt("New Name   [" + s.getName()   + "]");
        String course = ConsoleUI.prompt("New Course [" + s.getCourse() + "]");
        String email  = ConsoleUI.prompt("New Email  [" + s.getEmail()  + "]");

        if (!name.isEmpty())   s.setName(name);
        if (!course.isEmpty()) s.setCourse(course);
        if (!email.isEmpty())  s.setEmail(email);

        if (studentDAO.updateStudent(s)) {
            ConsoleUI.printSuccess("Student updated successfully.");
        } else {
            ConsoleUI.printError("Update failed.");
        }
    }

    private void deleteStudent() {
        System.out.println();
        String id = ConsoleUI.prompt("Enter Student ID to delete");
        String confirm = ConsoleUI.prompt("Are you sure? (yes/no)");
        if ("yes".equalsIgnoreCase(confirm)) {
            if (studentDAO.deleteStudent(id)) {
                ConsoleUI.printSuccess("Student deleted.");
            } else {
                ConsoleUI.printError("Student not found.");
            }
        } else {
            ConsoleUI.printInfo("Cancelled.");
        }
    }

    private void searchStudent() {
        System.out.println();
        String id = ConsoleUI.prompt("Enter Student ID");
        Student s = studentDAO.findById(id);
        if (s == null) { ConsoleUI.printError("Not found."); return; }
        System.out.println();
        ConsoleUI.printTableHeader("ID", "Name", "Course", "Email");
        ConsoleUI.printTableRow(s.getId(), s.getName(), s.getCourse(), s.getEmail());
        ConsoleUI.printTableFooter(4);
    }
}
