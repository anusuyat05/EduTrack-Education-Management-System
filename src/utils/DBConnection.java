package utils;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * utils.DBConnection class provides methods to establish and close database connections.
 * It retrieves database credentials from environment variables for security purposes.
 */
public class DBConnection {
    public static Connection provideConnection() {
        Properties properties = new Properties();

        try (FileReader reader = new FileReader("C:\\Users\\Dell\\IdeaProjects\\sunware_project\\src\\.env")) {
            properties.load(reader);
            String url = properties.getProperty("DB_URL");
            String user = properties.getProperty("DB_USER");
            String password = properties.getProperty("DB_PASSWORD");

            return DriverManager.getConnection(url, user, password);
        } catch (IOException e) {
            throw new RuntimeException("Error: Failed to read database configuration file!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error: Database connection failed!", e);
        }
    }
    //Closes the provided database resources (Connection, Statement, ResultSet) safely.
    public static void closeResources(Connection connection, Statement Statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (Statement != null) Statement.close();
            if (connection != null) connection.close();
        } catch (SQLException sqlException) {
            System.err.println("Error closing database resources.");
            sqlException.fillInStackTrace();
        }
    }
}