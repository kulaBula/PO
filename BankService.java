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
        
        if (typ.equalsIgnoreCase("OSOBISTE")) {
            repo.addKonto(nrPesel, saldoPoczatkowe);
            return new KontoOsobiste(saldoPoczatkowe, parametrDodatkowy);
        } else if (typ.equalsIgnoreCase("OSZCZEDNOSCIOWE")) {
            repo.addKonto(nrPesel, saldoPoczatkowe);
            return new KontoOszczednosciowe(saldoPoczatkowe, parametrDodatkowy);
        }
        
        throw new IllegalArgumentException("Nieznany typ konta: " + typ);
    }
}