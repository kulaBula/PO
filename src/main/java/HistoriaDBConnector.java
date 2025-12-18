// klasa odpowiedzialna za połączenie z nowym plikiem bazy danych
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HistoriaDBConnector {
    private static final String URL = "jdbc:sqlite:HistoriaTransakcji.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}