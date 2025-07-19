package StudentManagementSystem;

import java.sql.*;

public class CoursesDAO {
    private Connection conn;

    public CoursesDAO() {
        conn = DBConnection.getConnection();
    }

    // Create new course
    public int addCourse(String name, String duration, double fees) {
        int result = 0;
        try {
            String query = "INSERT INTO courses (name, duration, fees) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, duration);
            ps.setDouble(3, fees);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
        return result;
    }

    // Update course details
    public int updateCourse(int courseId, String name, String duration, double fees) {
        int result = 0;
        try {
            String query = "UPDATE courses SET name = ?, duration = ?, fees = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, duration);
            ps.setDouble(3, fees);
            ps.setInt(4, courseId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating course: " + e.getMessage());
        }
        return result;
    }

    // Delete a course
    public int deleteCourse(int courseId) {
        int result = 0;
        try {
            String query = "DELETE FROM courses WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, courseId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting course: " + e.getMessage());
        }
        return result;
    }

    // View all courses
    public void viewAllCourses() {
        try {
            String query = "SELECT * FROM courses";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Available Courses:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id")
                        + ", Name: " + rs.getString("name")
                        + ", Duration: " + rs.getString("duration")
                        + ", Fees: " + rs.getDouble("fees"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving courses: " + e.getMessage());
        }
    }
}


