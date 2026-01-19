package ab_final_grafo;

public class Direccion {

    private int provincia;
    private int municipio;
    private int unidadPoblacional;
    private int via;
    private String calle; // Nombre legible de la calle

    // Constructor vacío para Jackson (JSON)
    public Direccion() {}

    public Direccion(int provincia, int municipio, int unidadPoblacional, int via) {
        this.provincia = provincia;
        this.municipio = municipio;
        this.unidadPoblacional = unidadPoblacional;
        this.via = via;
        this.calle = "Calle-" + via; // Por defecto
    }

    public Direccion(int provincia, int municipio, int unidadPoblacional, int via, String calle) {
        this.provincia = provincia;
        this.municipio = municipio;
        this.unidadPoblacional = unidadPoblacional;
        this.via = via;
        this.calle = calle;
    }

    // Getters y Setters
    public int getProvincia() {
        return provincia;
    }

    public void setProvincia(int provincia) {
        this.provincia = provincia;
    }

    public int getMunicipio() {
        return municipio;
    }

    public void setMunicipio(int municipio) {
        this.municipio = municipio;
    }

    public int getUnidadPoblacional() {
        return unidadPoblacional;
    }

    public void setUnidadPoblacional(int unidadPoblacional) {
        this.unidadPoblacional = unidadPoblacional;
    }

    public int getVia() {
        return via;
    }

    public void setVia(int via) {
        this.via = via;
    }

    public String getCalle() {
        return calle != null ? calle : "Calle-" + via;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * Nombre único para identificar la dirección
     */
    public String getNombre() {
        return provincia + "-" + municipio + "-" + unidadPoblacional + "-" + via;
    }

    @Override
    public String toString() {
        return getCalle() + " (" + getNombre() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion that = (Direccion) o;
        return provincia == that.provincia &&
                municipio == that.municipio &&
                unidadPoblacional == that.unidadPoblacional &&
                via == that.via;
    }

    @Override
    public int hashCode() {
        return getNombre().hashCode();
    }
}