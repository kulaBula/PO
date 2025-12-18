import java.sql.*;

public class BazaKlientow {
    public static void main(String[] args) {
        // Używamy naszego nowego connectora
        try (Connection conn = ClientDBConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            
            System.out.println("Połączono z bazą Klientów!");
            
            // Tworzenie tabeli z peselem jako kluczem unikalnym
            String createTableSQL = "CREATE TABLE IF NOT EXISTS klienci (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "imie TEXT, " +
                    "nazwisko TEXT, " +
                    "pesel TEXT UNIQUE, " +
                    "email TEXT, " +
                    "haslo TEXT)";
            
            stmt.execute(createTableSQL);
            System.out.println("Tabela 'klienci' została utworzona/zweryfikowana.");

        } catch (SQLException e) {
            System.err.println("Błąd bazy klientów: " + e.getMessage());
        }
    }
}