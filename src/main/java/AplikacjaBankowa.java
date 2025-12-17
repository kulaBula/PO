public class AplikacjaBankowa {
    public static void main(String[] args) {
        // Pobranie instancji serwisu
        BankService bankService = BankService.getInstance();

        // Tworzenie kont za pomocą Factory
        Konto mojeOsobiste = bankService.stworzKonto("OSOBISTE", 1000.0, 500.0, "1");
        Konto mojeOszczednosci = bankService.stworzKonto("OSZCZEDNOSCIOWE", 5000.0, 0.05, "2");

        // Testowanie działań
        System.out.println("--- Test Konta Osobistego ---");
        mojeOsobiste.wyplac(1200.0); // Powinno wejść w debet
        
        System.out.println("\n--- Test Konta Oszczędnościowego ---");
        mojeOszczednosci.wplac(500.0);
        
        // Wywołanie metody specyficznej dla klasy (wymaga rzutowania)
        if (mojeOszczednosci instanceof KontoOszczednosciowe) {
            ((KontoOszczednosciowe) mojeOszczednosci).naliczOprocentowanie();
            System.out.println("Po naliczeniu odsetek: " + mojeOszczednosci.getSaldo());
        }
    }
}