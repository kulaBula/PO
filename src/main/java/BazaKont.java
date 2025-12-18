import java.sql.*;

public class BazaKont {
    private static final String URL = "jdbc:sqlite:./bazaKont.db";
    public static void main(String[] args){
        try(Connection conn = DriverManager.getConnection(URL);
        Statement stmt = conn.createStatement()) {
            System.out.println("Połączono z bazą SQLite!");
            System.out.println("\n--- 1. Tworzenie tabeli ---");
            // Uwaga: W SQLite używamy INTEGER PRIMARY KEY AUTOINCREMENT
            String createTableSQLO = "CREATE TABLE IF NOT EXISTS konta (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " + //nr konta
                    "nrPesel TEXT, " + //nr pesel aby powiązać konta z klientami
                    "typ TEXT, " + 
                    "limitDebetu FLOAT, " +
                    "saldo FLOAT)";
            stmt.execute(createTableSQLO);
            System.out.println("Tabela gotowa.");

            // System.out.println("\n--- 2. Wstawianie danych ---");
            // String insertSQL = "INSERT INTO konta (imie, email) VALUES ('Adam Nowak', 'adam@poczta.pl')";
            // stmt.executeUpdate(insertSQL);
            // System.out.println("Dodano rekord.");

            System.out.println("\n--- 3. Odczyt danych ---");
            ResultSet rs = stmt.executeQuery("SELECT * FROM konta");
            while (rs.next()) {
                // Dopasuj nazwy kolumn do tych z CREATE TABLE
                System.out.println("PESEL: " + rs.getString("nrPesel") + 
                                " | Saldo: " + rs.getDouble("saldo"));
            }
        
        }catch (SQLException e) {
            System.err.println("Błąd SQLite: " + e.getMessage());
        }
    }
}
