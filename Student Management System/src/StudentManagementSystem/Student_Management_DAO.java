package StudentManagementSystem;

import java.sql.*;

public class Student_Management_DAO {
   private Connection conn;
   private PreparedStatement ps;
    private ResultSet rs;

    public Student_Management_DAO() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_Management", "Username", "your_password");
        } catch (SQLException ex) {
            System.out.println("Connection Failed: " + ex.getMessage());
            throw new RuntimeException("Failed to initialize DAO", ex);
        }
    }

    public int createStudent(long rollno, String name, long phoneNo, String email) {
        int result = 0;
        try {
            ps = conn.prepareStatement("INSERT INTO Student VALUES (?, ?, ?, ?)");
            ps.setLong(1, rollno);
            ps.setString(2, name);
            ps.setLong(3, phoneNo);
            ps.setString(4, email);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Insert Failed: " + ex.getMessage());
            return -1;
        }finally {
        	closeResources(null, ps, null);
        }
        return result;
    }
 private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
	 try {
		 if(rs!= null) rs.close();
		 if(ps!= null) ps.close();
	 }
	 catch(SQLException e) {
		 System.out.println("Error closing resources: "+ e.getMessage());
	 }
 }
    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            System.out.println("Closing Connection Failed: " + ex.getMessage());
        }
    }
}

