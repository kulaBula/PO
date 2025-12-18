import java.util.Scanner;

public class AplikacjaBankowa {
    public static void main(String[] args) {
        BankService bankService = BankService.getInstance();
        KlienciRepository klienciRepo = new KlienciRepository();
        // Potrzebujemy dostępu do repozytorium kont, aby wyświetlić listę
        KontaRepository kontaRepo = new KontaRepository(); 
        
        Scanner scanner = new Scanner(System.in);
        boolean aplikacjaDziala = true;

        System.out.println("=== SYSTEM BANKOWY 2.0 ===");

        while (aplikacjaDziala) {
            System.out.println("\n--- MENU STARTOWE ---");
            System.out.println("1. Zaloguj się");
            System.out.println("2. Zarejestruj się");
            System.out.println("3. Wyjście");
            System.out.print("Wybór: ");
            String opcjaStart = scanner.nextLine();

            switch (opcjaStart) {
                case "1": // LOGOWANIE
                    System.out.print("Podaj PESEL: ");
                    String peselLogin = scanner.nextLine();
                    System.out.print("Podaj hasło: ");
                    String hasloLogin = scanner.nextLine();

                    Klient zalogowanyKlient = klienciRepo.zaloguj(peselLogin, hasloLogin);

                    if (zalogowanyKlient != null) {
                        System.out.println("\nWitaj " + zalogowanyKlient.imie + " " + zalogowanyKlient.nazwisko + "!");
                        obslugaZalogowanegoUzytkownika(scanner, bankService, kontaRepo, zalogowanyKlient);
                    } else {
                        System.out.println("Błąd: Nieprawidłowy PESEL lub hasło.");
                    }
                    break;

                case "2": // REJESTRACJA
                    System.out.println("\n--- REJESTRACJA ---");
                    System.out.print("Imię: "); String imie = scanner.nextLine();
                    System.out.print("Nazwisko: "); String nazwisko = scanner.nextLine();
                    System.out.print("PESEL: "); String peselReg = scanner.nextLine();
                    System.out.print("Email: "); String email = scanner.nextLine();
                    System.out.print("Hasło: "); String hasloReg = scanner.nextLine();

                    Klient nowyKlient = new Klient(imie, nazwisko, peselReg, email, hasloReg);
                    klienciRepo.dodajKlienta(nowyKlient);
                    break;

                case "3":
                    aplikacjaDziala = false;
                    System.out.println("Zamykanie systemu.");
                    break;
                default:
                    System.out.println("Nieznana opcja.");
            }
        }
    }

    // Metoda obsługująca menu po zalogowaniu
    private static void obslugaZalogowanegoUzytkownika(Scanner scanner, BankService bankService, KontaRepository kontaRepo, Klient klient) {
        boolean zalogowany = true;
        while (zalogowany) {
            System.out.println("\n--- MENU UŻYTKOWNIKA (" + klient.pesel + ") ---");
            System.out.println("1. Moje konta (Wyświetl)");
            System.out.println("2. Utwórz nowe konto");
            System.out.println("3. Przelew");
            System.out.println("4. Wyloguj");
            System.out.print("Wybór: ");
            
            String opcja = scanner.nextLine();

            switch (opcja) {
                case "1":
                    // Wyświetlenie kont tylko tego użytkownika
                    kontaRepo.wyswietlKontaKlienta(klient.pesel);
                    break;

                case "2":
                    // Logika tworzenia konta (podobna do poprzedniej, ale PESEL bierzemy z sesji)
                    try {
                        System.out.println("Typ: 1-OSOBISTE, 2-OSZCZĘDNOŚCIOWE");
                        String wybor = scanner.nextLine();
                        String typ = wybor.equals("1") ? "OSOBISTE" : (wybor.equals("2") ? "OSZCZEDNOSCIOWE" : null);

                        if(typ == null) {
                            System.out.println("Zly wybór.");
                            break;
                        }

                        System.out.print("Saldo początkowe: ");
                        double saldo = Double.parseDouble(scanner.nextLine());
                        
                        String prompt = typ.equals("OSOBISTE") ? "Limit debetu: " : "Oprocentowanie (np 0.05): ";
                        System.out.print(prompt);
                        double param = Double.parseDouble(scanner.nextLine());

                        // Używamy PESELu zalogowanego klienta!
                        bankService.stworzKonto(typ, saldo, param, klient.pesel);
                        
                    } catch (Exception e) {
                        System.out.println("Błąd danych: " + e.getMessage());
                    }
                    break;

                case "3":
                    try {
                        System.out.println("Wybierz konto, z którego chcesz wykonać przelew: ");
                        kontaRepo.wyswietlKontaKlienta(klient.pesel);
        
                        System.out.print("Numer Twojego konta: ");
                        int kontoNadawcyId = Integer.parseInt(scanner.nextLine()); // Bezpieczniejsze niż nextInt()

                        Konto aktywneKonto = kontaRepo.zaladujKonto(kontoNadawcyId, klient.pesel);

                        if (aktywneKonto != null) {
                            System.out.print("Podaj numer konta odbiorcy: ");
                            int kontoOdbiorcyId = Integer.parseInt(scanner.nextLine());
            
                            System.out.print("Podaj kwotę: ");
                            double kwota = Double.parseDouble(scanner.nextLine());

                        // Wywołanie logiki przelewu z BankService (rekomendowane)
                            aktywneKonto.przelew(kontoOdbiorcyId, kwota);
                        } else {
                            System.out.println("Błąd: Nie znaleziono Twojego konta o tym ID.");
                        }
                    } catch (NumberFormatException e) {
                    System.out.println("Błąd: Podaj poprawną liczbę.");
                    }
                    break;
                case "4":
                    zalogowany = false;
                    System.out.println("Wylogowano.");
                    break;
                default:
                    System.out.println("Nieznana opcja.");
            }
        }
    }
}