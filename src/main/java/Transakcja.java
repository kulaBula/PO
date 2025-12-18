// dodałem zapisywanie danych transakcji do pliku HistoriaTransakcji.db
import java.time.LocalDateTime;

public class Transakcja {

    private int nadawca;
    private int odbiorca;
    private double kwota;
    private LocalDateTime data;
    private KontaRepository repo = new KontaRepository();
    // 1. DODAJEMY TO POLE:
    private HistoriaRepository historiaRepo = new HistoriaRepository(); 

    public Transakcja(int nrKonta, int nrKontaOdbiorca, double kwota) {
        this.nadawca = nrKonta;
        this.odbiorca = nrKontaOdbiorca;
        this.kwota = kwota;
        this.data = LocalDateTime.now(); //
    }

    public void wykonaj() {
        Double saldoNadawcy = repo.getSaldo(this.nadawca); //
        Double saldoOdbiorcy = repo.getSaldo(this.odbiorca); //

        if (kwota <= 0) {
            throw new IllegalArgumentException("Kwota musi być większa od zera"); //
        }
        if (saldoNadawcy < kwota) { // Mała poprawka logiczna: sprawdzamy czy starczy na przelew
            throw new IllegalStateException("Brak środków na koncie"); //
        }

        // Aktualizacja sald w głównej bazie kont
        repo.updateSaldo(this.nadawca, saldoNadawcy - kwota); //
        repo.updateSaldo(this.odbiorca, saldoOdbiorcy + kwota); //

        // 2. TUTAJ DOPISUJEMY ZAPIS DO HISTORII:
        historiaRepo.zapiszTransakcje(this.nadawca, this.odbiorca, this.kwota, "PRZELEW");

        System.out.println("Transakcja od: " + this.nadawca + " do: " + this.odbiorca + " została pomyślnie zakończona o: " + this.data); //
    }

    public LocalDateTime getData() {
        return data; //
    }
}