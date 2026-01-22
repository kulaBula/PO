import java.sql.Connection;
import java.sql.Statement;

public class TESTY {

    public static void main(String[] args) {
        System.out.println("=== ROZPOCZYNAM TESTY INTEGRACYJNE (NA BAZIE TESTOWEJ) ===");
        zresetujBazeTestowa();
        boolean t1 = testWplaty();
        boolean t2 = testDebetu();
        boolean t3 = testPrzelewu();
        System.out.println("\n--------------------------------------------------");
        if (t1 && t2 && t3) {
            System.out.println("WYNIK KOŃCOWY: WSZYSTKIE TESTY ZALICZONE (3/3)");
        } else {
            System.out.println("WYNIK KOŃCOWY: NIEKTÓRE TESTY NIE PRZESZŁY");
        }
    }
    private static void zresetujBazeTestowa() {
        try (Connection conn = TESTConnectorBazaKont.getConnection();
             Statement stmt = conn.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS konta (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                    "nrPesel TEXT, " + 
                    "typ TEXT, " + 
                    "limitDebetu FLOAT, " +
                    "saldo FLOAT)";
            stmt.execute(createTableSQL);
            stmt.execute("DELETE FROM konta");
            stmt.execute("DELETE FROM sqlite_sequence WHERE name='konta'");
            System.out.println("[SETUP] Baza testowa wyczyszczona i gotowa.");
        } catch (Exception e) {
            System.out.println("[SETUP BŁĄD] Nie udało się zresetować bazy: " + e.getMessage());
        }
    }

    // Testy:
    private static boolean testWplaty() {
        System.out.print("\nTest 1: Wpłata na konto... ");
        TESTRepoKonta testRepo = new TESTRepoKonta();
        int idKonta = testRepo.addKontoOsobiste("12345678901", 100.0, 500.0);
        KontoOsobiste konto = new KontoOsobiste(100.0, 500.0, idKonta, testRepo);
        konto.wplac(50.0);
        double saldoWBazie = testRepo.getSaldo(idKonta);
        if (saldoWBazie == 150.0 && konto.getSaldo() == 150.0) {
            System.out.println("OK");
            return true;
        } else {
            System.out.println("BŁĄD (Oczekiwano: 150.0, Baza: " + saldoWBazie + ", Obiekt: " + konto.getSaldo() + ")");
            return false;
        }
    }

    private static boolean testDebetu() {
        System.out.print("Test 2: Wypłata z debetem... ");
        TESTRepoKonta testRepo = new TESTRepoKonta();
        int idKonta = testRepo.addKontoOsobiste("98765432100", 0.0, 200.0);
        KontoOsobiste konto = new KontoOsobiste(0.0, 200.0, idKonta, testRepo);
        konto.wyplac(100.0);
        double saldoWBazie = testRepo.getSaldo(idKonta);
        if (saldoWBazie == -100.0) {
            System.out.println("OK");
            return true;
        } else {
            System.out.println("BŁĄD (Oczekiwano: -100.0, jest: " + saldoWBazie + ")");
            return false;
        }
    }

    private static boolean testPrzelewu() {
        System.out.print("Test 3: Przelew między kontami... ");
        TESTRepoKonta testRepo = new TESTRepoKonta();
        int idNadawca = testRepo.addKontoOsobiste("111", 1000.0, 0.0);
        int idOdbiorca = testRepo.addKontoOsobiste("222", 500.0, 0.0);
        Transakcja t = new Transakcja(idNadawca, idOdbiorca, 200.0, testRepo);
        t.wykonaj();
        double sNadawca = testRepo.getSaldo(idNadawca);
        double sOdbiorca = testRepo.getSaldo(idOdbiorca);

        if (sNadawca == 800.0 && sOdbiorca == 700.0) {
            System.out.println("OK");
            return true;
        } else {
            System.out.println("BŁĄD (Nadawca: " + sNadawca + " [oczekiwano 800], Odbiorca: " + sOdbiorca + " [oczekiwano 700])");
            return false;
        }
    }
}