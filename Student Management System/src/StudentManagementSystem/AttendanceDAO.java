package StudentManagementSystem;

import java.sql.*;
import java.util.Date;

public class AttendanceDAO {
    private Connection conn;

    public AttendanceDAO() {
        conn = DBConnection.getConnection();
    }

    // 1. Mark attendance
    public int markAttendance(int studentId, int courseId, String status, java.sql.Date date) {
        int result = 0;
        try {
            String query = "INSERT INTO attendance (student_id, course_id, date, status) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setDate(3, date);
            ps.setString(4, status);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error marking attendance: " + e.getMessage());
        }
        return result;
    }

    // 2. View attendance for a student
    public void viewAttendanceByStudent(int studentId) {
        try {
            String query = "SELECT a.date, c.name AS course_name, a.status " +
                           "FROM attendance a JOIN courses c ON a.course_id = c.id " +
                           "WHERE a.student_id = ? ORDER BY a.date DESC";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            System.out.println("Attendance for Student ID: " + studentId);
            while (rs.next()) {
                System.out.println("Date: " + rs.getDate("date")
                        + ", Course: " + rs.getString("course_name")
                        + ", Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing attendance: " + e.getMessage());
        }
    }

    // 3. Attendance summary (total presents and absents)
    public void viewAttendanceSummary(int studentId, int courseId) {
        try {
            String query = "SELECT status, COUNT(*) AS total " +
                           "FROM attendance WHERE student_id = ? AND course_id = ? GROUP BY status";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ResultSet rs = ps.executeQuery();

            System.out.println("Attendance Summary for Student ID: " + studentId + ", Course ID: " + courseId);
            while (rs.next()) {
                System.out.println(rs.getString("status") + ": " + rs.getInt("total"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving summary: " + e.getMessage());
        }
    }
}

