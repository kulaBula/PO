public class BankService {
    private static BankService instance;

    private BankService() {}

    public static BankService getInstance() {
        if (instance == null) {
            instance = new BankService();
        }
        return instance;
    }
    KontaRepository repo = new KontaRepository();
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
        int idNowegoKonta = repo.addKonto(nrPesel, saldoPoczatkowe); // Tworzymy nowe konto w bazie i pobieramy id nowego konta
        if (typ.equalsIgnoreCase("OSOBISTE")) {
            return new KontoOsobiste(saldoPoczatkowe, parametrDodatkowy, idNowegoKonta);
        } else if (typ.equalsIgnoreCase("OSZCZEDNOSCIOWE")) {
            return new KontoOszczednosciowe(saldoPoczatkowe, parametrDodatkowy, idNowegoKonta);
        }
        
        throw new IllegalArgumentException("Nieznany typ konta: " + typ);
    }
}