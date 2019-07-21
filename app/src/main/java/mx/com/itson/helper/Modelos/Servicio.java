package mx.com.itson.helper.Modelos;

import java.sql.Date;
import java.util.Calendar;

public class Servicio {

    int id;
    int idUsuarioCliente;
    int idProveedor;
    String fechaOrden;
    String fechaServicio;
    String descripcionNecesidades;
    boolean aceptado;
    boolean completado;

    public Servicio() {
    }

    public Servicio(int id, int idUsuarioCliente, int idProveedor, String fechaOrden, String fechaServicio, String descripcionNecesidades, boolean aceptado, boolean completado) {
        this.id = id;
        this.idUsuarioCliente = idUsuarioCliente;
        this.idProveedor = idProveedor;
        this.fechaOrden = fechaOrden;
        this.fechaServicio = fechaServicio;
        this.descripcionNecesidades = descripcionNecesidades;
        this.aceptado = aceptado;
        this.completado = completado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuarioCliente() {
        return idUsuarioCliente;
    }

    public void setIdUsuarioCliente(int idUsuarioCliente) {
        this.idUsuarioCliente = idUsuarioCliente;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(String fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(String fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public String getDescripcionNecesidades() {
        return descripcionNecesidades;
    }

    public void setDescripcionNecesidades(String descripcionNecesidades) {
        this.descripcionNecesidades = descripcionNecesidades;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}