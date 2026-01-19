package ab_final_grafo;
import org.jgrapht.graph.DefaultEdge;

public class SiguiendoArista {

    private final String since;

    public SiguiendoArista(String since) {
        this.since = since;
    }

    public String getSince() {
        return since;
    }

    @Override
    public String toString() {
        return "follow(" + since + ")";
    }
}