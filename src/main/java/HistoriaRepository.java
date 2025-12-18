// Klasa odpowiedzialna za zapisywanie i odczytywanie historii (robienie wyciągu)
import java.sql.*;
import java.time.LocalDateTime;

public class HistoriaRepository {
    public HistoriaRepository() {
        inicjalizujBaze(); // Wywołujemy tworzenie tabeli przy każdym utworzeniu obiektu repozytorium
    }

    private void inicjalizujBaze() {
        String sql = "CREATE TABLE IF NOT EXISTS historia (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "id_nadawcy INTEGER, " +
                     "id_adresata INTEGER, " +
                     "kwota DOUBLE, " +
                     "data TEXT, " +
                     "typ TEXT)";
        try (Connection conn = HistoriaDBConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Błąd inicjalizacji bazy historii: " + e.getMessage());
        }
    }

    /*public void inicjalizujBaze() {
    String sql = "CREATE TABLE IF NOT EXISTS historia (" +
                 "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 "id_nadawcy INTEGER, " +
                 "id_adresata INTEGER, " +
                 "kwota DOUBLE, " +
                 "data TEXT, " +
                 "typ TEXT)";
    try (Connection conn = HistoriaDBConnector.getConnection();
         Statement stmt = conn.createStatement()) {
        stmt.execute(sql);
    } catch (SQLException e) {
        System.err.println("Błąd inicjalizacji bazy historii: " + e.getMessage());
    }
}*/
    
    public void zapiszTransakcje(int nadawca, int odbiorca, double kwota, String typ) {
        String sql = "INSERT INTO historia (id_nadawcy, id_adresata, kwota, data, typ) VALUES(?, ?, ?, ?, ?)";
        
        try (Connection conn = HistoriaDBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, nadawca);
            pstmt.setInt(2, odbiorca);
            pstmt.setDouble(3, kwota);
            pstmt.setString(4, LocalDateTime.now().toString());
            pstmt.setString(5, typ);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Błąd zapisu historii: " + e.getMessage());
        }
    }

    public void wyswietlWyciag(int nrKonta) {
        String sql = "SELECT * FROM historia WHERE id_nadawcy = ? OR id_adresata = ? ORDER BY data DESC";
        
        try (Connection conn = HistoriaDBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, nrKonta);
            pstmt.setInt(2, nrKonta);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- HISTORIA OPERACJI DLA KONTA: " + nrKonta + " ---");
            while (rs.next()) {
                String data = rs.getString("data");
                double kwota = rs.getDouble("kwota");
                int od = rs.getInt("id_nadawcy");
                int doKogo = rs.getInt("id_adresata");
                String typ = rs.getString("typ");

                String kierunek = (od == nrKonta) ? "WYCHODZĄCA (Do: " + doKogo + ")" : "PRZYCHODZĄCA (Od: " + od + ")";
                System.out.println(data + " | " + typ + " | " + kierunek + " | Kwota: " + kwota + " PLN");
            }
        } catch (SQLException e) {
            System.err.println("Błąd odczytu historii: " + e.getMessage());
        }
    }
}