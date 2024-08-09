package Model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    public int id;
    public String tensanpham;
    public Integer giasp;
    public String hinhanhsp;
    public String mota;
    public int idsanpham;

    public Sanpham(int id, String tensanpham, Integer giasp, String hinhanhsp, String mota, int idsanpham) {
        this.id = id;
        this.tensanpham = tensanpham;
        this.giasp = giasp;
        this.hinhanhsp = hinhanhsp;
        this.mota = mota;
        this.idsanpham = idsanpham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public Integer getGiasp() {
        return giasp;
    }

    public void setGiasp(Integer giasp) {
        this.giasp = giasp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        this.idsanpham = idsanpham;
    }
}
