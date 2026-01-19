package ab_final_grafo;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.cycle.JohnsonSimpleCycles;
import org.jgrapht.alg.cycle.PatonCycleBase;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GraphAnalytics {

    public List<usario> topPorSeguidores(Graph<usario, SiguiendoArista> graph, int topN) {
        return graph.vertexSet().stream()
                .sorted(Comparator.comparingInt((usario u) -> graph.inDegreeOf(u)).reversed())
                .limit(topN)
                .toList();
    }

    public List<List<Direccion>> ciclosSimples(Graph<Direccion, DefaultWeightedEdge> graph) {
        var cycleBasis = new PatonCycleBase<>(graph).getCycleBasis();
        return cycleBasis.getCycles().stream()
                .map(cycleEdges -> {
                    if (cycleEdges == null || cycleEdges.isEmpty()) {
                        return List.<Direccion>of();
                    }

                    LinkedHashSet<Direccion> vertices = new LinkedHashSet<>();
                    for (DefaultWeightedEdge edge : cycleEdges) {
                        vertices.add(graph.getEdgeSource(edge));
                        vertices.add(graph.getEdgeTarget(edge));
                    }
                    return new ArrayList<>(vertices);
                })
                .toList();
    }

    public GraphPath<usario, SiguiendoArista> caminoMasCorto(
            Graph<usario, SiguiendoArista> graph,
            usario origen,
            usario destino
    ) {
        var dijkstra = new DijkstraShortestPath<>(graph);
        return dijkstra.getPath(origen, destino);
    }

    public Set<usario> sugerenciasSeguir(Graph<usario, SiguiendoArista> graph, usario origen) {
        // usuarios ya seguidos + uno mismo
        Set<usario> yaSiguiendo = graph.outgoingEdgesOf(origen).stream()
                .map(graph::getEdgeTarget)
                .collect(Collectors.toSet());
        yaSiguiendo.add(origen);

        Set<usario> sugerencias = new HashSet<>();
        var bfs = new BreadthFirstIterator<>(graph, origen);

        Map<usario, Integer> distancia = new HashMap<>();
        distancia.put(origen, 0);

        while (bfs.hasNext()) {
            usario actual = bfs.next();
            if (actual.equals(origen)) {
                continue;
            }

            usario padre = bfs.getParent(actual);
            int dist = distancia.getOrDefault(padre, 0) + 1;
            distancia.put(actual, dist);

            if (dist == 2 && !yaSiguiendo.contains(actual)) {
                sugerencias.add(actual);
            }
        }

        return sugerencias;
    }

    public usario findByEmail(Graph<usario, SiguiendoArista> graph, String email) {
        return graph.vertexSet().stream()
                .filter(u -> Objects.equals(u.email(), email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + email));
    }
}