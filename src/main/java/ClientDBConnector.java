import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClientDBConnector {
    // Wskazujemy na nowy plik bazy danych dla klientów
    private static final String URL = "jdbc:sqlite:./bazaKlientow.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}