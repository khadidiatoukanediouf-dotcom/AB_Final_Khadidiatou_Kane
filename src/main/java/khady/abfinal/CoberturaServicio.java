package khady.abfinal;

import khady.abfinal.exchange.PeticionCliente;
import java.util.HashSet;

/**
 * Servicio de validacion de cobertura
 *
 * Usa HashSet porque permite busquedas O(1), lo cual es optimo
 * para ficheros grandes (cientos de miles de lineas).
 */
public class CoberturaServicio {

    // Estructuras de datos
    private HashSet<String> provincias;
    private HashSet<String> municipios;
    private HashSet<String> unidadesPoblacionales;
    private HashSet<String> vias;

    // Constructor
    public CoberturaServicio() {
        provincias = new HashSet<>();
        municipios = new HashSet<>();
        unidadesPoblacionales = new HashSet<>();
        vias = new HashSet<>();
    }

    /**
     * Comprueba si una peticion esta cubierta
     * Busqueda O(1) en cada HashSet = muy rapido
     */
    public boolean damosServicio(PeticionCliente direccion) {
        return provincias.contains(direccion.getProvincia())
                && municipios.contains(direccion.getMunicipio())
                && unidadesPoblacionales.contains(direccion.getUnidadPoblacional())
                && vias.contains(direccion.getVia());
    }

    // Metodos para añadir datos
    public void addProvincia(String provincia) {
        if (provincia != null && !provincia.isEmpty()) {
            provincias.add(provincia);
        }
    }

    public void addMunicipio(String municipio) {
        if (municipio != null && !municipio.isEmpty()) {
            municipios.add(municipio);
        }
    }

    public void addUnidadPoblacional(String unidadPoblacional) {
        if (unidadPoblacional != null && !unidadPoblacional.isEmpty()) {
            unidadesPoblacionales.add(unidadPoblacional);
        }
    }

    public void addVia(String via) {
        if (via != null && !via.isEmpty()) {
            vias.add(via);
        }
    }

    // Metodos de consulta
    public int getTotalProvincias() {
        return provincias.size();
    }

    public int getTotalMunicipios() {
        return municipios.size();
    }

    public int getTotalUnidadesPoblacionales() {
        return unidadesPoblacionales.size();
    }

    public int getTotalVias() {
        return vias.size();
    }
}