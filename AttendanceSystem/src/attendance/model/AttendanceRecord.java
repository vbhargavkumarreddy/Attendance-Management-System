package attendance.model;

public class AttendanceRecord {
    private String studentId;
    private String date;       // Format: yyyy-MM-dd
    private String status;     // "PRESENT", "ABSENT", "LATE"
    private String markedBy;

    public AttendanceRecord(String studentId, String date, String status, String markedBy) {
        this.studentId = studentId;
        this.date = date;
        this.status = status;
        this.markedBy = markedBy;
    }

    public String getStudentId() { return studentId; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public String getMarkedBy() { return markedBy; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return studentId + "," + date + "," + status + "," + markedBy;
    }
}
