package mx.com.itson.helper.Modelos;

public class Usuario {

    int id;
    String uid;
    String direccion;
    boolean ofreceServicio;

    public Usuario() {
    }


    public Usuario(int id, String uid, String direccion, boolean ofreceServicio) {
        this.id = id;
        this.uid = uid;
        this.direccion = direccion;
        this.ofreceServicio = ofreceServicio;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isOfreceServicio() {
        return ofreceServicio;
    }

    public void setOfreceServicio(boolean ofreceServicio) {
        this.ofreceServicio = ofreceServicio;
    }
}
