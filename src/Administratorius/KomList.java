package Administratorius;

public class KomList {


    private String nik;
    private String data;
    private String kom;
    private String prek;
    private String id;

    public KomList(String id, String prek, String nik, String data, String kom) {

        this.id = id;
        this.prek = prek;
        this.nik = nik;
        this.data= data;
        this.kom = kom;

    }

    public String getPrek() {
        return prek;
    }

    public void setPrek(String prek) {
        this.prek = prek;
    }

    public String getKom() {
        return kom;
    }

    public void setKom(String kom) {
        this.kom = kom;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
