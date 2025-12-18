import java.sql.*;

public class KontaRepository {
    public int addKontoOsobiste(String nrPesel, Double saldo, Double limitDebetu) {
        String sql = "INSERT INTO konta (nrPesel, saldo, typ, limitDebetu) VALUES(?, ?, ?, ?)";

        try (Connection conn = DataBaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nrPesel);
            pstmt.setDouble(2, saldo);
            pstmt.setString(3, "OSOBISTE");
            pstmt.setDouble(4, limitDebetu);
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if(generatedKeys.next()){
                return generatedKeys.getInt(1);
            }
            System.out.println("Konto osobiste dodane!");
            
        } catch (SQLException e) {
            System.out.println("Błąd podczas dodawania: " + e.getMessage());
        }
        return -1;
    }
    public int addKontoOszczednosciowe(String nrPesel, Double saldo) {
        String sql = "INSERT INTO konta (nrPesel, typ, saldo) VALUES(?, ?, ?)";

        try (Connection conn = DataBaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nrPesel);
            pstmt.setString(2, "OSZCZEDNOSCIOWE");
            pstmt.setDouble(3, saldo);
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if(generatedKeys.next()){
                return generatedKeys.getInt(1);
            }
            System.out.println("Konto osczednosciowe dodane!");
            
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
                String typ = rs.getString("typ");
                double saldo = rs.getDouble("saldo");
                System.out.println("Konto" + typ + ": Numer konta: " + id + " | Saldo: " + saldo + " PLN");
            }
            
            if(!znaleziono) {
                System.out.println("Brak otwartych kont dla numeru PESEL: " + peselKlienta);
            }
            
        } catch (SQLException e) {
            System.out.println("Błąd pobierania kont: " + e.getMessage());
        }
    }
    public Double getSaldo(int nrKonta){
        Double saldo = 0.0;
        String sql = "SELECT saldo FROM konta WHERE id = ?";
        try (Connection conn = DataBaseConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, nrKonta);
            ResultSet rs = pstmt.executeQuery();

            saldo = rs.getDouble("saldo");
        } catch (SQLException e) {
        System.out.println("Błąd pobierania salda " + e.getMessage());
        }
        return saldo;
    }
    
    public Konto zaladujKonto(int nrKonta, String nrPesel) {
        String sql = "SELECT * FROM konta WHERE id = ? AND nrPesel = ?";
        //System.out.println("ladowanie konta "+ nrKonta + " " + nrPesel);
        //System.out.println("Próba ładowania konta ID: " + nrKonta + " dla PESEL: " + nrPesel);
        try (Connection conn = DataBaseConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // System.out.println("Helo");
            // System.out.println("Łączę się z bazą: " + conn.getMetaData().getURL());
            
            pstmt.setInt(1, nrKonta);
            pstmt.setString(2, nrPesel);
            
            ResultSet rs = pstmt.executeQuery();

            String typ = rs.getString("typ");
            if("OSZCZEDNOSCIOWE".equals(typ)){
                return new KontoOszczednosciowe(rs.getDouble("saldo"), nrKonta);
            }
            else{
                return new KontoOsobiste(rs.getDouble("saldo"), rs.getDouble("limitDebetu"), nrKonta);
            }

        } catch (SQLException e) {
            System.out.println("Błąd ladowania konta " + e.getMessage());
        }
        return null; // Nie znaleziono użytkownika lub błędne hasło
    }
}
