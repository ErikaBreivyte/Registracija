package Prekės;

public class KategorList {

    private String id;
    private String pavadin;


    public KategorList (String id, String pavadin) {
        this.id = id;
        this.pavadin = pavadin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPavadin() {
        return pavadin;
    }

    public void setPavadin(String pavadin) {
        this.pavadin = pavadin;
    }
}
