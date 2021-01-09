package sample;

public class UserList {

    private String vardas;
    private String pavarde;
    private String nikas;
    private String pass;
    private String metai;
    private String menesis;
    private String diena;



    public UserList (String vardas, String pavarde, String nikas, String pass, String metai, String menesins, String diena){
        this.vardas = vardas;
        this.pavarde = pavarde;
        this.nikas = nikas;
        this.pass = pass;
        this.metai= metai;
        this.menesis = menesins;
        this.diena = diena;

    }

    public String getVardas() {
        return vardas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public String getPavarde() {
        return pavarde;
    }

    public void setPavarde(String pavarde) {
        this.pavarde = pavarde;
    }

    public String getNikas() {
        return nikas;
    }

    public void setNikas(String nikas) {
        this.nikas = nikas;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMetai() {
        return metai;
    }

    public void setMetai(String metai) {
        this.metai = metai;
    }

    public String getMenesis() {
        return menesis;
    }

    public void setMenesis(String menesis) {
        this.menesis = menesis;
    }

    public String getDiena() {
        return diena;
    }

    public void setDiena(String diena) {
        this.diena = diena;
    }
}
