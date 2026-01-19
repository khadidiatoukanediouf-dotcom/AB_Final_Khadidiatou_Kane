package khady.abfinal.exchange;

/**
 * Representa una petición de un cliente con su dirección.
 * Los códigos se tratan como String porque provienen de fichero
 * y pueden contener ceros a la izquierda o letras.
 */
public class PeticionCliente {

    private String provincia;
    private String municipio;
    private String unidadPoblacional;
    private String via;

    /// CONSTRUCTOR COMPLETO
    public PeticionCliente(String provincia,
                           String municipio,
                           String unidadPoblacional,
                           String via) {

        this.provincia = provincia;
        this.municipio = municipio;
        this.unidadPoblacional = unidadPoblacional;
        this.via = via;
    }

    public PeticionCliente(int i, int i1, int i2, int i3, int i4) {
    }

    /// GETTERS
    public String getProvincia() {
        return provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getUnidadPoblacional() {
        return unidadPoblacional;
    }

    public String getVia() {
        return via;
    }

    @Override
    public String toString() {
        return "PeticionCliente{" +
                "provincia='" + provincia + '\'' +
                ", municipio='" + municipio + '\'' +
                ", unidadPoblacional='" + unidadPoblacional + '\'' +
                ", via='" + via + '\'' +
                '}';
    }
}
