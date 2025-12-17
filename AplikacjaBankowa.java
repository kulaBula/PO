import java.util.Scanner;

public class AplikacjaBankowa {
    public static void main(String[] args) {
        // Pobranie instancji serwisu (Singleton)
        BankService bankService = BankService.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean uruchomiona = true;

        System.out.println("=== SYSTEM BANKOWY ===");

        while (uruchomiona) {
            System.out.println("\n--- MENU GŁÓWNE ---");
            System.out.println("1. Utwórz nowe konto");
            System.out.println("2. Wyświetl wszystkie konta (Baza danych)");
            System.out.println("3. Wyjście");
            System.out.print("Wybór: ");

            String opcja = scanner.nextLine();

            switch (opcja) {
                case "1":
                    try {
                        System.out.print("Podaj PESEL: ");
                        String pesel = scanner.nextLine();

                        System.out.println("Wybierz typ konta:");
                        System.out.println("1 - OSOBISTE");
                        System.out.println("2 - OSZCZĘDNOŚCIOWE");
                        System.out.print("Wybór: ");
                        String wyborTypu = scanner.nextLine();

                        String typDlaService = "";
                        if (wyborTypu.equals("1")) {
                            typDlaService = "OSOBISTE";
                        } else if (wyborTypu.equals("2")) {
                            typDlaService = "OSZCZEDNOSCIOWE";
                        } else {
                            System.out.println("Błąd: Nieprawidłowy wybór typu.");
                            break;
                        }

                        System.out.print("Saldo początkowe: ");
                        double saldo = Double.parseDouble(scanner.nextLine());

                        // Zmiana komunikatu w zależności od typu konta
                        String promptParametr = typDlaService.equals("OSOBISTE") ? 
                            "Limit debetu: " : "Oprocentowanie (np. 0.05): ";
                        System.out.print(promptParametr);
                        double parametr = Double.parseDouble(scanner.nextLine());

                        // Wykorzystanie wzorca Factory do stworzenia i zapisu konta
                        bankService.stworzKonto(typDlaService, saldo, parametr, pesel);
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Błąd: Podano nieprawidłowy format liczby.");
                    } catch (Exception e) {
                        System.out.println("Wystąpił nieoczekiwany błąd: " + e.getMessage());
                    }
                    break;

                case "2":
                    System.out.println("\n--- LISTA KONT W BAZIE ---");
                    // Wywołanie metody main z BazaKont, aby wyświetlić rekordy
                    BazaKont.main(null);
                    break;

                case "3":
                    uruchomiona = false;
                    System.out.println("Zamykanie systemu. Do widzenia!");
                    break;

                default:
                    System.out.println("Błąd: Wybrano nieznaną opcję.");
            }
        }
        scanner.close();
    }
}