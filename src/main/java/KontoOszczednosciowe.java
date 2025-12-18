public class KontoOszczednosciowe implements Konto {
    private double saldo;
    private final double oprocentowanie;
    private int nrKonta;
    private KontaRepository repo = new KontaRepository();
    // Konstruktor
    public KontoOszczednosciowe(double poczatkoweSaldo, double oprocentowanie, int idKonta){
        this.saldo = poczatkoweSaldo;
        this.oprocentowanie = oprocentowanie;
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
    @Override public boolean przelew(String nrKontaAdresata, Double kwota){
        //Uzupełnić 
        return true;
        
    }
    @Override public double getSaldo(){
        return this.saldo;
    }
    public void naliczOprocentowanie() {
        this.saldo *= (1 + this.oprocentowanie);
    }
}
