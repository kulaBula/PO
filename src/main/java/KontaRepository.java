import java.sql.*;

public class KontaRepository {
    public int addKonto(String nrPesel, Double saldo) {
        String sql = "INSERT INTO konta(nrPesel, saldo) VALUES(?, ?)";

        try (Connection conn = DataBaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nrPesel);
            pstmt.setDouble(2, saldo);
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if(generatedKeys.next()){
                return generatedKeys.getInt(1);
            }
            System.out.println("Konto dodane!");
            
        } catch (SQLException e) {
            System.out.println("Błąd podczas dodawania: " + e.getMessage());
        }
        return -1;
    }
    public void updateSaldo(int idKonta, double noweSaldo) {
    String sql = "UPDATE konta SET saldo = ? WHERE id = ?";

    try (Connection conn = DataBaseConnector.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setDouble(1, noweSaldo);
        pstmt.setInt(2, idKonta);
        
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Baza danych zaktualizowana (numer Konta: " + idKonta + ").");
        }
        
    } catch (SQLException e) {
        System.err.println("Błąd aktualizacji salda: " + e.getMessage());
    }
    }
    public void wyswietlKontaKlienta(String peselKlienta) {
        String sql = "SELECT * FROM konta WHERE nrPesel = ?";
        
        try (Connection conn = DataBaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, peselKlienta);
            ResultSet rs = pstmt.executeQuery();
            
            boolean znaleziono = false;
            System.out.println("\n--- TWOJE KONTA ---");
            while(rs.next()) {
                znaleziono = true;
                int id = rs.getInt("id");
                double saldo = rs.getDouble("saldo");
                System.out.println("ID Konta: " + id + " | Saldo: " + saldo + " PLN");
            }
            
            if(!znaleziono) {
                System.out.println("Brak otwartych kont dla numeru PESEL: " + peselKlienta);
            }
            
        } catch (SQLException e) {
            System.out.println("TUU Błąd pobierania kont: " + e.getMessage());
        }
    }
}
