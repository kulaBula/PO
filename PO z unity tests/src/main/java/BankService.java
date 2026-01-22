public class BankService {
    private static BankService instance;
    private RepoKonta repo = new RepoKonta();
    private RepoKlienci repoKlienci = new RepoKlienci();
    private BankService() {}

    public static BankService getInstance() {
        if (instance == null) {
            instance = new BankService();
        }
        return instance;
    }
    public void wyswietlKontaKlienta(String pesel) {
        // Serwis deleguje zadanie do repozytorium
        repo.wyswietlKontaKlienta(pesel);
    }
    public Konto pobierzKontoKlienta(int idKonta, String pesel) {
        // Logika biznesowa: Sprawdzamy, czy konto o danym ID należy do tego PESELu
        return repo.zaladujKonto(idKonta, pesel);
    }
    public void zarejestrujKlienta(String imie, String nazwisko, String pesel, String email, String haslo) {
        // Logika biznesowa: Tworzymy obiekt klienta i zapisujemy w repozytorium
        Klient nowyKlient = new Klient(imie, nazwisko, pesel, email, haslo);
        repoKlienci.dodajKlienta(nowyKlient);
        System.out.println("System: Rejestracja zakończona sukcesem dla: " + pesel);
    }
    public Klient zalogujKlienta(String pesel, String haslo) {
        return repoKlienci.zaloguj(pesel, haslo);
    }

    /**
     * Metoda Factory do tworzenia kont.
     * @param typ "OSOBISTE" lub "OSZCZEDNOSCIOWE"
     * @param saldoPoczatkowe kwota na start
     * @param parametrDodatkowy limit debetu dla osobistego LUB oprocentowanie dla oszczędnościowego
     */
    public Konto stworzKonto(String typ, double saldoPoczatkowe, double parametrDodatkowy, String nrPesel) {
        if (typ == null) {
            return null;
        }
        if (typ.equalsIgnoreCase("OSOBISTE")) {
            int idNowegoKonta = repo.addKontoOsobiste(nrPesel, saldoPoczatkowe, parametrDodatkowy);
            return new KontoOsobiste(saldoPoczatkowe, parametrDodatkowy, idNowegoKonta);
        } else if (typ.equalsIgnoreCase("OSZCZEDNOSCIOWE")) {
            int idNowegoKonta = repo.addKontoOszczednosciowe(nrPesel, saldoPoczatkowe);
            return new KontoOszczednosciowe(saldoPoczatkowe, idNowegoKonta);
        }
        
        throw new IllegalArgumentException("Nieznany typ konta: " + typ);
    }
}