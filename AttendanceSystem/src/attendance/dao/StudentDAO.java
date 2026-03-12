package attendance.dao;

import attendance.model.Student;
import attendance.util.FileUtil;

import java.util.*;

public class StudentDAO {
    private static final String FILE = "data/students.csv";

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        for (String line : FileUtil.readLines(FILE)) {
            String[] parts = line.split(",", 4);
            if (parts.length == 4) {
                students.add(new Student(parts[0], parts[1], parts[2], parts[3]));
            }
        }
        return students;
    }

    public Student findById(String id) {
        for (Student s : getAllStudents()) {
            if (s.getId().equalsIgnoreCase(id)) return s;
        }
        return null;
    }

    public boolean addStudent(Student student) {
        if (findById(student.getId()) != null) return false;
        FileUtil.appendLine(FILE, student.toString());
        return true;
    }

    public boolean updateStudent(Student updated) {
        List<Student> students = getAllStudents();
        List<String> lines = new ArrayList<>();
        boolean found = false;
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(updated.getId())) {
                lines.add(updated.toString());
                found = true;
            } else {
                lines.add(s.toString());
            }
        }
        if (found) FileUtil.writeLines(FILE, lines);
        return found;
    }

    public boolean deleteStudent(String id) {
        List<Student> students = getAllStudents();
        List<String> lines = new ArrayList<>();
        boolean found = false;
        for (Student s : students) {
            if (!s.getId().equalsIgnoreCase(id)) {
                lines.add(s.toString());
            } else {
                found = true;
            }
        }
        if (found) FileUtil.writeLines(FILE, lines);
        return found;
    }

    public List<Student> getByCoure(String course) {
        List<Student> result = new ArrayList<>();
        for (Student s : getAllStudents()) {
            if (s.getCourse().equalsIgnoreCase(course)) result.add(s);
        }
        return result;
    }

    public void initializeSampleData() {
        if (getAllStudents().isEmpty()) {
            addStudent(new Student("S001", "Alice Johnson", "Computer Science", "alice@example.com"));
            addStudent(new Student("S002", "Bob Williams", "Computer Science", "bob@example.com"));
            addStudent(new Student("S003", "Carol Davis", "Mathematics", "carol@example.com"));
            addStudent(new Student("S004", "David Brown", "Mathematics", "david@example.com"));
            addStudent(new Student("S005", "Emma Wilson", "Physics", "emma@example.com"));
        }
    }
}
