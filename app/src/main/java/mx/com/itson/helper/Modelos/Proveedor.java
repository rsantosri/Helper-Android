package mx.com.itson.helper.Modelos;

import java.util.List;

public class Proveedor {
    int idUsuarioProveedor;
    int idCategoria;
    String nombre;
    String descripcion;
    String imagen;
    double costoPromedio;
    List<Oferta> oferta;

    public Proveedor() {
    }

    public Proveedor(int idUsuarioProveedor, int idCategoria, String nombre, String descripcion, String imagen, double costoPromedio, List<Oferta> oferta) {
        this.idUsuarioProveedor = idUsuarioProveedor;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.costoPromedio = costoPromedio;
        this.oferta = oferta;
    }

    public int getIdUsuarioProveedor() {
        return idUsuarioProveedor;
    }

    public void setIdUsuarioProveedor(int idUsuarioProveedor) {
        this.idUsuarioProveedor = idUsuarioProveedor;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getCostoPromedio() {
        return costoPromedio;
    }

    public void setCostoPromedio(double costoPromedio) {
        this.costoPromedio = costoPromedio;
    }

    public List<Oferta> getOferta() {
        return oferta;
    }

    public void setOferta(List<Oferta> oferta) {
        this.oferta = oferta;
    }
}
