package khady.abfinal.service;

import khady.abfinal.CoberturaServicio;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.InitializingBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Servicio que lee el archivo TRAM y carga las direcciones
 * cubiertas en memoria usando HashSet para busqueda O(1)
 */
@Service
public class TramoService implements InitializingBean {

    private CoberturaServicio coberturaServicio;

    /**
     * Se ejecuta automaticamente al iniciar Spring Boot
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Cargando archivo...");
        long inicio = System.currentTimeMillis();

        try {
            coberturaServicio = leerTramos();
            long tiempo = System.currentTimeMillis() - inicio;
            System.out.println("Cargado en " + tiempo + " ms");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Lee el archivo TRAM y extrae los datos por posiciones fijas
     *
     * Formato del archivo:
     * - Posicion 0-2: Provincia
     * - Posicion 2-5: Municipio
     * - Posicion 5-12: Unidad poblacional
     * - Posicion 13-24: Via
     */
    public CoberturaServicio leerTramos() throws IOException {

        CoberturaServicio cobertura = new CoberturaServicio();
        ClassPathResource resource = new ClassPathResource("TRAM.D250630.G250702");

        if (!resource.exists()) {
            throw new IOException("Archivo no existe");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.ISO_8859_1))) {

            String linea;
            boolean isEmpty = true;

            while ((linea = reader.readLine()) != null) {

                if (linea.trim().isEmpty() || linea.length() < 24) {
                    continue;
                }

                isEmpty = false;

                try {
                    // Extraer campos por posiciones
                    String provincia = extraer(linea, 0, 2);
                    String municipio = extraer(linea, 2, 5);
                    String unidadPoblacional = extraer(linea, 5, 12);
                    String via = extraer(linea, 13, 24);

                    // Guardar si no estan vacios
                    if (!provincia.isEmpty()) cobertura.addProvincia(provincia);
                    if (!municipio.isEmpty()) cobertura.addMunicipio(municipio);
                    if (!unidadPoblacional.isEmpty()) cobertura.addUnidadPoblacional(unidadPoblacional);
                    if (!via.isEmpty()) cobertura.addVia(via);

                } catch (Exception e) {
                    // Ignorar lineas mal formadas
                }
            }

            if (isEmpty) {
                throw new IOException("Archivo vacio");
            }

            return cobertura;
        }
    }

    /**
     * Extrae un campo del archivo y elimina espacios
     */
    private String extraer(String linea, int inicio, int fin) {
        if (linea.length() < fin) return "";
        return linea.substring(inicio, fin).trim().replaceAll(" ", "");
    }

    public CoberturaServicio getCoberturaServicio() {
        return coberturaServicio;
    }
}