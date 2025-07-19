package StudentManagementSystem;

import java.sql.*;
import java.util.Date;

public class FeedbackDAO {
    private Connection conn;

    public FeedbackDAO() {
        conn = DBConnection.getConnection();
    }

    // Insert feedback
    public int submitFeedback(int studentId, int courseId, String feedbackText) {
        int result = 0;
        try {
            String query = "INSERT INTO feedback (student_id, course_id, feedback_text, submitted_on) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setString(3, feedbackText);
            ps.setDate(4, new java.sql.Date(new Date().getTime()));
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error submitting feedback: " + e.getMessage());
        }
        return result;
    }
    
    

    // View feedback for a course
    public void viewCourseFeedback(int courseId) {
        try {
            String query = "SELECT s.name, f.feedback_text, f.submitted_on " +
                           "FROM feedback f JOIN students s ON f.student_id = s.id " +
                           "WHERE f.course_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            System.out.println("Feedback for Course ID: " + courseId);
            while (rs.next()) {
                System.out.println("Student: " + rs.getString("name"));
                System.out.println("Feedback: " + rs.getString("feedback_text"));
                System.out.println("Date: " + rs.getDate("submitted_on"));
                System.out.println("-----------");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving feedback: " + e.getMessage());
        }
    }
}
