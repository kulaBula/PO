public interface Konto {
    public Double wplac(Double kwota);
    public Double wyplac(Double kwota);
    public void przelew(int nrKontaAdresata, Double kwota);
    public double getSaldo();
}
