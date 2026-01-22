public interface Repository {
    int addKontoOsobiste(String nrPesel, Double saldo, Double limitDebetu);
    int addKontoOszczednosciowe(String nrPesel, Double saldo);
    void updateSaldo(int idKonta, double noweSaldo);
    Double getSaldo(int nrKonta);
    Konto zaladujKonto(int nrKonta, String nrPesel);
}