package ab_final_grafo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@AllArgsConstructor
public class Unit19Application implements CommandLineRunner {

    @Autowired
    private final GraphLoader graphLoader;

    @Autowired
    private final GraphAnalytics graphAnalytics;

    public static void main(String[] args) {
        SpringApplication.run(Unit19Application.class, args);
    }

    @Override
    public void run(String... args) {

        // CARGA DEL GRAFO DE REPARTO

        Graph<Direccion, DefaultWeightedEdge> grafoDeReparto = graphLoader.loadUserGraph();


        // VISUALIZACIÓN CON JGRAPHT / JGRAPHX
        GraphViewer.show(
                grafoDeReparto,
                Direccion::getNombre,   // etiqueta de nodos
                e -> {
                    double peso = grafoDeReparto.getEdgeWeight(e);
                    if (Math.floor(peso) == peso) {
                        return String.valueOf((long) peso);
                    }
                    return String.format("%.2f", peso);
                }
        );


        // VISUALIZACIÓN CON GRAPHSTREAM

        GraphViewer.pintar(grafoDeReparto);

        // ANÁLISIS DE GRAFO

        log.info("Ciclos simples en el grafo: {}", graphAnalytics.ciclosSimples(grafoDeReparto).toString());
        log.info("Número de direcciones (vértices): {}", grafoDeReparto.vertexSet().size());
        log.info("Número de trayectos (aristas): {}", grafoDeReparto.edgeSet().size());
    }
}
