/*import java.sql.*;

public class BazaKont {
    private static final String URL = "jdbc:sqlite:./bazaKont.db";
    public static void main(String[] args){
        try(Connection conn = DriverManager.getConnection(URL);
        Statement stmt = conn.createStatement()) {
            System.out.println("Połączono z bazą SQLite!");
            System.out.println("\n--- 1. Tworzenie tabeli ---");
            // Uwaga: W SQLite używamy INTEGER PRIMARY KEY AUTOINCREMENT
            String createTableSQL = "CREATE TABLE IF NOT EXISTS konta (" +
                    "nrPesel TEXT, " + 
                    "saldo FLOAT)";
            stmt.execute(createTableSQL);
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
*/

import java.sql.*;

public class BazaKont {
    // Ścieżka do bazy danych (taka sama jak w DataBaseConnector)
    private static final String URL = "jdbc:sqlite:./bazaKont.db";

    public static void main(String[] args) {
        try {
            // Wymuszenie załadowania sterownika JDBC dla SQLite
            // Pomaga to uniknąć błędu "No suitable driver found" przy ręcznym uruchamianiu
            Class.forName("org.sqlite.JDBC");

            try (Connection conn = DriverManager.getConnection(URL);
                 Statement stmt = conn.createStatement()) {
                
                System.out.println("Połączono z bazą SQLite!");
                System.out.println("\n--- 1. Tworzenie tabeli ---");
                
                // Tabela musi mieć kolumny zgodne z tym, co przesyłasz w KontaRepository
                // nrPesel (TEXT) oraz saldo (REAL/FLOAT)
                String createTableSQL = "CREATE TABLE IF NOT EXISTS konta (" +
                                        "nrPesel TEXT, " + 
                                        "saldo REAL)";
                
                stmt.execute(createTableSQL);
                System.out.println("Tabela 'konta' jest gotowa.");

                System.out.println("\n--- 2. Aktualny stan bazy danych ---");
                ResultSet rs = stmt.executeQuery("SELECT * FROM konta");
                
                boolean pusta = true;
                while (rs.next()) {
                    pusta = false;
                    System.out.println("PESEL: " + rs.getString("nrPesel") + 
                                       " | Saldo: " + rs.getDouble("saldo"));
                }
                
                if (pusta) {
                    System.out.println("Baza jest obecnie pusta.");
                }

            }
        } catch (ClassNotFoundException e) {
            System.err.println("Nie znaleziono sterownika SQLite! Upewnij się, że plik JAR jest w classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Błąd SQLite: " + e.getMessage());
            e.printStackTrace();
        }
    }
}