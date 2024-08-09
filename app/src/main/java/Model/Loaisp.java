package Model;

public class Loaisp {
    public int id;
    public String tenloaisanpham;
    public String hinhanhloaisp;

    public Loaisp(int id, String tenloaisanpham, String hinhanhloaisp) {
        this.id = id;
        this.tenloaisanpham = tenloaisanpham;
        this.hinhanhloaisp = hinhanhloaisp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTenloaisanpham(String tenloaisanpham) {
        this.tenloaisanpham = tenloaisanpham;
    }

    public void setHinhanhloaisp(String hinhanhloaisp) {
        this.hinhanhloaisp = hinhanhloaisp;
    }

    public int getId() {
        return id;
    }

    public String getTenloaisanpham() {
        return tenloaisanpham;
    }

    public String getHinhanhloaisp() {
        return hinhanhloaisp;
    }
}
