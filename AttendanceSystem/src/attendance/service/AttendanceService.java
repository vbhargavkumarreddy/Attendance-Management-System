package attendance.service;

import attendance.dao.AttendanceDAO;
import attendance.dao.StudentDAO;
import attendance.model.AttendanceRecord;
import attendance.model.Student;
import attendance.util.DateUtil;

import java.util.*;

public class AttendanceService {
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final AuthService auth = AuthService.getInstance();

    public boolean markAttendance(String studentId, String date, String status) {
        if (!auth.isLoggedIn()) return false;
        if (studentDAO.findById(studentId) == null) return false;
        if (!DateUtil.isValidDate(date)) return false;
        if (!Arrays.asList("PRESENT", "ABSENT", "LATE").contains(status.toUpperCase())) return false;

        AttendanceRecord record = new AttendanceRecord(
            studentId, date, status.toUpperCase(), auth.getCurrentUser().getUsername()
        );
        return attendanceDAO.markAttendance(record);
    }

    public void markAllForDate(String date, Map<String, String> studentStatusMap) {
        for (Map.Entry<String, String> entry : studentStatusMap.entrySet()) {
            markAttendance(entry.getKey(), date, entry.getValue());
        }
    }

    public List<AttendanceRecord> getByDate(String date) {
        return attendanceDAO.getRecordsByDate(date);
    }

    public List<AttendanceRecord> getByStudent(String studentId) {
        return attendanceDAO.getRecordsByStudent(studentId);
    }

    public Map<String, Integer> getSummary(String studentId) {
        return attendanceDAO.getAttendanceSummary(studentId);
    }

    public double getPercentage(String studentId) {
        return attendanceDAO.getAttendancePercentage(studentId);
    }

    public List<Map<String, Object>> getFullReport() {
        List<Map<String, Object>> report = new ArrayList<>();
        for (Student s : studentDAO.getAllStudents()) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", s.getId());
            row.put("name", s.getName());
            row.put("course", s.getCourse());
            Map<String, Integer> summary = getSummary(s.getId());
            row.put("total", summary.get("TOTAL"));
            row.put("present", summary.get("PRESENT"));
            row.put("absent", summary.get("ABSENT"));
            row.put("late", summary.get("LATE"));
            row.put("percentage", String.format("%.1f%%", getPercentage(s.getId())));
            report.add(row);
        }
        return report;
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }
}
