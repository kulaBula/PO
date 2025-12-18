public class KontoOszczednosciowe implements Konto {
    private double saldo;
    private final double oprocentowanie = 0.05;
    private int nrKonta;
    private KontaRepository repo = new KontaRepository();
    // Konstruktor
    public KontoOszczednosciowe(double poczatkoweSaldo, int idKonta){
        this.saldo = poczatkoweSaldo;
        this.nrKonta = idKonta;
    };
    // Implementacja
    @Override public Double wplac(Double kwota){
        if(kwota > 0){
            this.saldo += kwota;
            repo.updateSaldo(this.nrKonta, this.saldo);
            System.out.println("Wpłacono " + kwota + ". Nowe saldo: " + this.saldo);
        }
        return this.saldo;
    }
    @Override public Double wyplac(Double kwota){
        if (kwota > 0 && saldo >= kwota) {
            saldo -= kwota;
            repo.updateSaldo(this.nrKonta, this.saldo);
            System.out.println("Wypłacono " + kwota + ". Nowe saldo: " + saldo);
            return this.saldo;
        }
        System.out.println("Brak wystarczających środków lub niepoprawna kwota do wypłaty.");
        return this.saldo;
    }
    @Override public void przelew(int nrKontaAdresata, Double kwota){
        Transakcja t = new Transakcja(this.nrKonta, nrKontaAdresata, kwota);
        t.wykonaj();
    }
    @Override public double getSaldo(){
        return this.saldo;
    }
    public void naliczOprocentowanie() {
        this.saldo *= (1 + this.oprocentowanie);
    }
}
