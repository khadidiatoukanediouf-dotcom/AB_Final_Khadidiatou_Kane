package ab_final_grafo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GraphLoader {

    private final ObjectMapper mapper;

    public GraphLoader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Graph<Direccion, DefaultWeightedEdge> loadUserGraph() {

        try {
            // ----------------------------
            // 1. Cargar direcciones
            // ----------------------------
            List<Direccion> direcciones = mapper.readValue(
                    new ClassPathResource("direcciones.json").getInputStream(),
                    new TypeReference<List<Direccion>>() {}
            );

            // Mapa clave única → Dirección
            // Clave basada en los códigos administrativos
            Map<String, Direccion> mapaDirecciones = direcciones.stream()
                    .collect(Collectors.toMap(Direccion::getNombre, d -> d));

            // ----------------------------
            // 2. Cargar trayectos
            // ----------------------------
            List<Trayecto> trayectos = mapper.readValue(
                    new ClassPathResource("trayectos.json").getInputStream(),
                    new TypeReference<List<Trayecto>>() {}
            );

            // ----------------------------
            // 3. Crear grafo
            // ----------------------------
            Graph<Direccion, DefaultWeightedEdge> grafoDeReparto =
                    new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

            // Añadir vértices
            direcciones.forEach(grafoDeReparto::addVertex);

            // Añadir aristas con peso
            for (Trayecto trayecto : trayectos) {

                Direccion origen = mapaDirecciones.get(trayecto.getDir1());
                Direccion destino = mapaDirecciones.get(trayecto.getDir2());

                if (origen != null && destino != null) {
                    DefaultWeightedEdge edge = grafoDeReparto.addEdge(origen, destino);
                    if (edge != null) {
                        grafoDeReparto.setEdgeWeight(edge, trayecto.getTime());
                    }
                }
            }

            return grafoDeReparto;

        } catch (IOException e) {
            throw new RuntimeException("Error cargando el grafo de reparto", e);
        }
    }
}
