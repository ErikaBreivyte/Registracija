package Prekės;

public class prekList {

    private String k_id;
    private String pav;
    private String apras;
    private String kain;

    public prekList (String k_id, String pav,String apras, String kain) {

        this.k_id = k_id;
        this.pav = pav;
        this.apras= apras;
        this.kain = kain;
    }

    public String getKain() {
        return kain;
    }

    public void setKain(String kain) {
        this.kain = kain;
    }

    public String getApras() {
        return apras;
    }

    public void setApras(String apras) {
        this.apras = apras;
    }

    public String getPav() {
        return pav;
    }

    public void setPav(String pav) {
        this.pav = pav;
    }

    public String getK_id() {
        return k_id;
    }

    public void setK_id(String k_id) {
        this.k_id = k_id;
    }
}
