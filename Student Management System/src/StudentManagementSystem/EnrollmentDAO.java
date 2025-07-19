package StudentManagementSystem;

import java.sql.*;

public class EnrollmentDAO {
    private Connection conn;

    public EnrollmentDAO() {
        conn = DBConnection.getConnection();
    }

    // Enroll a student in a course
    public int enrollStudent(int studentId, int courseId) {
        int result = 0;
        try {
            String query = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            result = ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Enrollment already exists or invalid IDs.");
        } catch (SQLException e) {
            System.out.println("Error during enrollment: " + e.getMessage());
        }
        return result;
    }

    // View enrolled courses for a student
    public void viewEnrolledCourses(int studentId) {
        try {
            String query = "SELECT c.courseName FROM enrollments e JOIN courses c ON e.course_id = c.id WHERE e.student_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            System.out.println("Courses enrolled by Student ID " + studentId + ":");
            while (rs.next()) {
                System.out.println("- " + rs.getString("courseName"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving enrolled courses: " + e.getMessage());
        }
    }
            
    public int removeEnrollment(int studentId, int courseId) {
        int result = 0;
        try {
            String query = "DELETE FROM enrollments WHERE student_id = ? AND course_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error removing enrollment: " + e.getMessage());
        }
        return result;
    }
}           
 
