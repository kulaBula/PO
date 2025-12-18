public class Klient {
    String imie;
    String nazwisko;
    String pesel;
    String adresEmail;
    String nrTelefonu;
    String miasto;
    String ulica;
    Integer nrDomu;
    String haslo;
    public Klient(String imie, String nazwisko, String pesel, String adresEmail, String haslo) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.adresEmail = adresEmail;
        this.haslo = haslo;
    }
    public Klient(){}
}
