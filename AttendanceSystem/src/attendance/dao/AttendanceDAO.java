package attendance.dao;

import attendance.model.AttendanceRecord;
import attendance.util.FileUtil;

import java.util.*;

public class AttendanceDAO {
    private static final String FILE = "data/attendance.csv";

    public List<AttendanceRecord> getAllRecords() {
        List<AttendanceRecord> records = new ArrayList<>();
        for (String line : FileUtil.readLines(FILE)) {
            String[] parts = line.split(",", 4);
            if (parts.length == 4) {
                records.add(new AttendanceRecord(parts[0], parts[1], parts[2], parts[3]));
            }
        }
        return records;
    }

    public AttendanceRecord findRecord(String studentId, String date) {
        for (AttendanceRecord r : getAllRecords()) {
            if (r.getStudentId().equalsIgnoreCase(studentId) && r.getDate().equals(date)) return r;
        }
        return null;
    }

    public boolean markAttendance(AttendanceRecord record) {
        // Update if exists, else append
        List<AttendanceRecord> all = getAllRecords();
        boolean found = false;
        List<String> lines = new ArrayList<>();
        for (AttendanceRecord r : all) {
            if (r.getStudentId().equalsIgnoreCase(record.getStudentId()) && r.getDate().equals(record.getDate())) {
                lines.add(record.toString());
                found = true;
            } else {
                lines.add(r.toString());
            }
        }
        if (!found) {
            lines.add(record.toString());
        }
        FileUtil.writeLines(FILE, lines);
        return true;
    }

    public List<AttendanceRecord> getRecordsByDate(String date) {
        List<AttendanceRecord> result = new ArrayList<>();
        for (AttendanceRecord r : getAllRecords()) {
            if (r.getDate().equals(date)) result.add(r);
        }
        return result;
    }

    public List<AttendanceRecord> getRecordsByStudent(String studentId) {
        List<AttendanceRecord> result = new ArrayList<>();
        for (AttendanceRecord r : getAllRecords()) {
            if (r.getStudentId().equalsIgnoreCase(studentId)) result.add(r);
        }
        return result;
    }

    public Map<String, Integer> getAttendanceSummary(String studentId) {
        List<AttendanceRecord> records = getRecordsByStudent(studentId);
        int present = 0, absent = 0, late = 0;
        for (AttendanceRecord r : records) {
            switch (r.getStatus().toUpperCase()) {
                case "PRESENT": present++; break;
                case "ABSENT":  absent++;  break;
                case "LATE":    late++;    break;
            }
        }
        Map<String, Integer> summary = new LinkedHashMap<>();
        summary.put("TOTAL", records.size());
        summary.put("PRESENT", present);
        summary.put("ABSENT", absent);
        summary.put("LATE", late);
        return summary;
    }

    public double getAttendancePercentage(String studentId) {
        Map<String, Integer> s = getAttendanceSummary(studentId);
        int total = s.get("TOTAL");
        if (total == 0) return 0.0;
        int present = s.get("PRESENT") + s.get("LATE"); // late counts as present
        return (present * 100.0) / total;
    }
}
