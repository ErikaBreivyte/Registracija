package Registruotas;

public class NoraiList {

    private String vard;
    private String prek;
    private String id;


    public NoraiList (String id, String vard, String prek) {
        this.id = id;
        this.vard= vard;
        this.prek = prek;

    }

    public String getVard() {
        return vard;
    }

    public void setVard(String vard) {
        this.vard = vard;
    }

    public String getPrek() {
        return prek;
    }

    public void setPrek(String prek) {
        this.prek = prek;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
