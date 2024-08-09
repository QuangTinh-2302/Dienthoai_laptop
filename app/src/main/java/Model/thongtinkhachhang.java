package Model;

public class thongtinkhachhang {
    private int idkh;
    private String tenkh,diachikh,sdtkh,emailkh;

    public thongtinkhachhang(int idkh, String tenkh, String diachikh, String sdtkh, String emailkh) {
        this.idkh = idkh;
        this.tenkh = tenkh;
        this.diachikh = diachikh;
        this.sdtkh = sdtkh;
        this.emailkh = emailkh;
    }

    public int getIdkh() {
        return idkh;
    }

    public void setIdkh(int idkh) {
        this.idkh = idkh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getDiachikh() {
        return diachikh;
    }

    public void setDiachikh(String diachikh) {
        this.diachikh = diachikh;
    }

    public String getSdtkh() {
        return sdtkh;
    }

    public void setSdtkh(String sdtkh) {
        this.sdtkh = sdtkh;
    }

    public String getEmailkh() {
        return emailkh;
    }

    public void setEmailkh(String emailkh) {
        this.emailkh = emailkh;
    }
}
