import java.sql.*;

public class KlienciRepository {

    // Rejestracja nowego Klienta
    public void dodajKlienta(Klient k) {
        String sql = "INSERT INTO klienci(imie, nazwisko, pesel, email, haslo) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = ClientDBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, k.imie);
            pstmt.setString(2, k.nazwisko);
            pstmt.setString(3, k.pesel);
            pstmt.setString(4, k.adresEmail);
            pstmt.setString(5, k.haslo);
            
            pstmt.executeUpdate();
            System.out.println("Zarejestrowano pomyślnie klienta: " + k.imie + " " + k.nazwisko);
            
        } catch (SQLException e) {
            System.out.println("Błąd rejestracji (może taki PESEL już istnieje?): " + e.getMessage());
        }
    }

    // Metoda logowania - zwraca obiekt Klienta jeśli dane są poprawne, null jeśli nie
    public Klient zaloguj(String pesel, String haslo) {
        String sql = "SELECT * FROM klienci WHERE pesel = ? AND haslo = ?";

        try (Connection conn = ClientDBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pesel);
            pstmt.setString(2, haslo);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Jeśli znaleziono rekord, tworzymy obiekt
                return new Klient(
                    rs.getString("imie"),
                    rs.getString("nazwisko"),
                    rs.getString("pesel"),
                    rs.getString("email"),
                    rs.getString("haslo")
                );
            }
        } catch (SQLException e) {
            System.out.println("Błąd logowania: " + e.getMessage());
        }
        return null; // Nie znaleziono użytkownika lub błędne hasło
    }
}