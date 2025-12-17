public class BankService {
    private static BankService instance;

    private BankService() {}

    public static BankService getInstance() {
        if (instance == null) {
            instance = new BankService();
        }
        return instance;
    }

    /**
     * Metoda Factory do tworzenia kont.
     * @param typ "OSOBISTE" lub "OSZCZEDNOSCIOWE"
     * @param saldoPoczatkowe kwota na start
     * @param parametrDodatkowy limit debetu dla osobistego LUB oprocentowanie dla oszczędnościowego
     */
    public Konto stworzKonto(String typ, double saldoPoczatkowe, double parametrDodatkowy) {
        if (typ == null) {
            return null;
        }
        
        if (typ.equalsIgnoreCase("OSOBISTE")) {
            return new KontoOsobiste(saldoPoczatkowe, parametrDodatkowy);
        } else if (typ.equalsIgnoreCase("OSZCZEDNOSCIOWE")) {
            return new KontoOszczednosciowe(saldoPoczatkowe, parametrDodatkowy);
        }
        
        throw new IllegalArgumentException("Nieznany typ konta: " + typ);
    }
}