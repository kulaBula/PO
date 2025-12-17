public interface Konto {
    public Double wplac(Double kwota);
    public Double wyplac(Double kwota);
    public boolean przelew(String nrKontaAdresata, Double kwota);
    public double getSaldo();
}
