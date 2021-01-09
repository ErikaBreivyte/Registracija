package Registruotas;

public class UzsakList {

    private String nr;
    private String vard;
    private String dat;
    private String kiek;
    private String kain;


    public UzsakList (String nr, String vard, String dat, String kiek, String kain) {

        this.nr = nr;
        this.vard= vard;
        this.dat = dat;
        this.kiek = kiek;
        this.kain = kain;

    }


    public String getKain() {
        return kain;
    }

    public void setKain(String kain) {
        this.kain = kain;
    }

    public String getKiek() {
        return kiek;
    }

    public void setKiek(String kiek) {
        this.kiek = kiek;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getVard() {
        return vard;
    }

    public void setVard(String vard) {
        this.vard = vard;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }
}
