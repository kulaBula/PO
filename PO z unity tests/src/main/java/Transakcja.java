import java.time.LocalDateTime;

public class Transakcja {

    private int nadawca;
    private int odbiorca;
    private double kwota;
    private LocalDateTime data;
    private Repository repo;
    private RepoHistoria historiaRepo = new RepoHistoria(); 

    public Transakcja(int nrKonta, int nrKontaOdbiorca, double kwota) {
        this.nadawca = nrKonta;
        this.odbiorca = nrKontaOdbiorca;
        this.kwota = kwota;
        this.data = LocalDateTime.now(); //
    }
    public Transakcja(int nadawca, int odbiorca, double kwota, Repository repo) {
        this.nadawca = nadawca;
        this.odbiorca = odbiorca;
        this.kwota = kwota;
        this.data = LocalDateTime.now();
        this.repo = repo;
    }

    public void wykonaj() {
        Double saldoNadawcy = repo.getSaldo(this.nadawca); //
        Double saldoOdbiorcy = repo.getSaldo(this.odbiorca); //

        if (kwota <= 0) {
            throw new IllegalArgumentException("Kwota musi być większa od zera"); //
        }
        if (saldoNadawcy < kwota) {
            throw new IllegalStateException("Brak środków na koncie"); //
        }
        repo.updateSaldo(this.nadawca, saldoNadawcy - kwota); //
        repo.updateSaldo(this.odbiorca, saldoOdbiorcy + kwota); //
        historiaRepo.zapiszTransakcje(this.nadawca, this.odbiorca, this.kwota, "PRZELEW");
        System.out.println("Transakcja od: " + this.nadawca + " do: " + this.odbiorca + " została pomyślnie zakończona o: " + this.data); //
    }
    public LocalDateTime getData() {
        return data; 
    }
}