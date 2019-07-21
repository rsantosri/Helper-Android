package mx.com.itson.helper.Modelos;

public class Oferta {

    String nombre;
    double costo;
    String imagen;

    public Oferta() {
    }

    public Oferta(String nombre, double costo, String imagen) {
        this.nombre = nombre;
        this.costo = costo;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Oferta oferta = (Oferta) o;

        if (Double.compare(oferta.costo, costo) != 0) return false;
        if (!nombre.equals(oferta.nombre)) return false;
        return imagen != null ? imagen.equals(oferta.imagen) : oferta.imagen == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = nombre.hashCode();
        temp = Double.doubleToLongBits(costo);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (imagen != null ? imagen.hashCode() : 0);
        return result;
    }
}
