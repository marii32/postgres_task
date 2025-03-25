package academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;


public class Main {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/academy";
        String user = "postgres";
        String password = "1";

        int numStudents = 10;
        int numCourses = 10;
        int numExams = 15;

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            connection.setAutoCommit(false);

            String insertStudentQuery = "INSERT INTO Students (name, start_year) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(insertStudentQuery)) {
                for (int i = 1; i <= numStudents; i++) {
                    stmt.setString(1, "Student" + i);
                    stmt.setInt(2, ThreadLocalRandom.current().nextInt(2020, 2026));
                    stmt.executeUpdate();
                }
            }


            String insertCourseQuery = "INSERT INTO Courses (title, hours) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(insertCourseQuery)) {
                for (int i = 1; i <= numCourses; i++) {
                    stmt.setString(1, "Course" + i);
                    stmt.setInt(2, ThreadLocalRandom.current().nextInt(30, 101));
                    stmt.executeUpdate();
                }
            }


            String insertExamQuery = "INSERT INTO Exams (s_id, c_no, score) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(insertExamQuery)) {
                for (int i = 1; i <= numExams; i++) {
                    int studentId = ThreadLocalRandom.current().nextInt(1, numStudents + 1);
                    int courseId = ThreadLocalRandom.current().nextInt(1, numCourses + 1);
                    int score = ThreadLocalRandom.current().nextInt(2, 6);

                    stmt.setInt(1, studentId);
                    stmt.setInt(2, courseId);
                    stmt.setInt(3, score);
                    stmt.executeUpdate();
                }
            }

            connection.commit();
            System.out.println("Данные успешно добавлены!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}