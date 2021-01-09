package Uzsakymai;

public class PirkiniaiList {

    private String id;
    private String vardas;
    private String preke;
    private String kaina;


    public PirkiniaiList(String id, String vardas, String preke, String kaina) {

        this.id = id;
        this.vardas = vardas;
        this.preke = preke;
        this.kaina= kaina;

    }

    public String getKaina() {
        return kaina;
    }

    public void setKaina(String kaina) {
        this.kaina = kaina;
    }

    public String getPreke() {
        return preke;
    }

    public void setPreke(String preke) {
        this.preke = preke;
    }

    public String getVardas() {
        return vardas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
