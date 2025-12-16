public class KontoOsobiste implements Konto {
    private double saldo;
    private double limitDebetu; 
    public KontoOsobiste(double poczatkoweSaldo, double limitDebetu) {
        this.saldo = poczatkoweSaldo;
        this.limitDebetu = limitDebetu;
    }
     @Override public Double wplac(Double kwota){
        if(kwota > 0){
            this.saldo += kwota;
            System.out.println("Wpłacono " + kwota + ". Nowe saldo: " + this.saldo);
        }
        return this.saldo;
    }
    @Override public Double wyplac(Double kwota) {
        double mozliwaWyplata = saldo + limitDebetu;
        
        // Możliwa wypłata, o ile nie przekracza salda + limitu debetu
        if (kwota > 0 && kwota <= mozliwaWyplata) {
            saldo -= kwota;
            System.out.println("Wypłacono " + kwota + ". Nowe saldo: " + saldo);
            return saldo;
        }
        System.out.println("Kwota przekracza dostępny limit debetu (" + limitDebetu + ").");
        return saldo;
    }
    @Override public boolean przelew(String nrKontaAdresata, Double kwota){
        //Uzupełnić 
        return true;
        
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