import java.sql.*;

public class KontaRepository {
    public void addKonto(String nrPesel, Double saldo) {
        String sql = "INSERT INTO konta(nrPesel, saldo) VALUES(?, ?)";

        try (Connection conn = DataBaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nrPesel);
            pstmt.setDouble(2, saldo);
            pstmt.executeUpdate();
            System.out.println("Konto dodane!");
            
        } catch (SQLException e) {
            System.out.println("Błąd podczas dodawania: " + e.getMessage());
        }
    }
}
