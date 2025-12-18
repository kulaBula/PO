public class KontoOsobiste implements Konto {
    private double saldo;
    private double limitDebetu; 
    private int nrKonta;
    private KontaRepository repo = new KontaRepository();
    public KontoOsobiste(double poczatkoweSaldo, double limitDebetu, int idKonta) {
        this.saldo = poczatkoweSaldo;
        this.limitDebetu = limitDebetu;
        this.nrKonta = idKonta;
    }
     @Override public Double wplac(Double kwota){
        if(kwota > 0){
            this.saldo += kwota;
            repo.updateSaldo(this.nrKonta, this.saldo);
            System.out.println("Wpłacono " + kwota + ". Nowe saldo: " + this.saldo);
        }
        return this.saldo;
    }
    @Override public Double wyplac(Double kwota) {
        double mozliwaWyplata = saldo + limitDebetu;
        
        // Możliwa wypłata, o ile nie przekracza salda + limitu debetu
        if (kwota > 0 && kwota <= mozliwaWyplata) {
            saldo -= kwota;
            repo.updateSaldo(this.nrKonta, this.saldo);
            System.out.println("Wypłacono " + kwota + ". Nowe saldo: " + saldo);
            return saldo;
        }
        System.out.println("Kwota przekracza dostępny limit debetu (" + limitDebetu + ").");
        return saldo;
    }
    @Override public void przelew(int nrKontaAdresata, Double kwota){
        Transakcja t = new Transakcja(this.nrKonta, nrKontaAdresata, kwota);
        t.wykonaj();
    }
    @Override public double getSaldo(){
        return this.saldo;
    }
    public double getDostepnyLimit() {
        if (saldo >= 0) {
            return limitDebetu;
        } else {
            return limitDebetu + saldo;
        }
    }

}