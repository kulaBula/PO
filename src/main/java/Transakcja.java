import java.time.LocalDateTime;

public class Transakcja {

    private int nadawca;
    private int odbiorca;
    private double kwota;
    private LocalDateTime data;
    private KontaRepository repo = new KontaRepository();

    public Transakcja(int nrKonta, int nrKontaOdbiorca, double kwota) {
        this.nadawca = nrKonta;
        this.odbiorca = nrKontaOdbiorca;
        this.kwota = kwota;
        this.data = LocalDateTime.now();
    }

    public void wykonaj() {
        Double saldoNadawcy = repo.getSaldo(this.nadawca);
        Double saldoOdbiorcy = repo.getSaldo(this.odbiorca);
        if (kwota <= 0) {
            throw new IllegalArgumentException("Kwota musi być większa od zera");
        }
        if (saldoNadawcy <= 0.0) {
            throw new IllegalStateException("Brak środków na koncie");
        }
        repo.updateSaldo(this.nadawca, saldoNadawcy-kwota);
        repo.updateSaldo(this.odbiorca, saldoOdbiorcy+kwota);
        System.out.println("Transakcja od: "+ this.nadawca + " do: " + this.odbiorca +" została pomyślnie zakończona o: "+ this.data);
    }

    public LocalDateTime getData() {
        return data;
    }
}
