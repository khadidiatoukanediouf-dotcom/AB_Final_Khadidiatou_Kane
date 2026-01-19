package ab_final_grafo;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxConstants;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.view.Viewer;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;

import javax.swing.*;
import java.util.Map;

public class GraphViewer {

    private static Viewer graphStreamViewer;
    private static org.graphstream.graph.Graph graphStreamGraph;

    public static <V, E> void show(Graph<V, E> graph,
                                   java.util.function.Function<V, String> vertexLabel,
                                   java.util.function.Function<E, String> edgeLabel) {

        System.setProperty("java.awt.headless", "false");
        System.setProperty("org.graphstream.ui", "swing");

        // Adapter JGraphT -> JGraphX
        JGraphXAdapter<V, E> adapter = new JGraphXAdapter<>(graph);
        adapter.getStylesheet().getDefaultVertexStyle()
                .put(mxConstants.STYLE_FONTSIZE, 24);

        // Poner etiquetas (vértices y aristas)
        Map<V, mxICell> vToCell = adapter.getVertexToCellMap();
        for (var v : graph.vertexSet()) {
            Object cell = vToCell.get(v);
            adapter.getModel().setValue(cell, vertexLabel.apply(v));
        }

        Map<E, mxICell> eToCell = adapter.getEdgeToCellMap();
        for (var e : graph.edgeSet()) {
            Object cell = eToCell.get(e);
            adapter.getModel().setValue(cell, edgeLabel.apply(e));
        }

        // Layout
        mxCircleLayout layout = new mxCircleLayout(adapter);
        layout.setRadius(50);
        layout.execute(adapter.getDefaultParent());

        // Swing window
        JFrame frame = new JFrame("X Users Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mxGraphComponent graphComponent = new mxGraphComponent(adapter);
        graphComponent.zoomTo(0.50, true);
        frame.getContentPane().add(graphComponent);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void pintar(Graph<Direccion, DefaultWeightedEdge> graph) {
        System.setProperty("java.awt.headless", "false");
        System.setProperty("org.graphstream.ui", "swing");

        if (graphStreamViewer != null) {
            graphStreamViewer.close();
            graphStreamViewer = null;
        }

        // Crear el grafo visual (GraphStream)
        graphStreamGraph = new DefaultGraph("pp");
        org.graphstream.graph.Graph gs = graphStreamGraph;

        // --- Estilo visual (CSS) - SIN TEXT BLOCKS ---
        String styleSheet =
                "node {\n" +
                        "  fill-color: #3366cc;\n" +
                        "  size: 25px;\n" +
                        "  text-size: 16px;\n" +
                        "  text-alignment: at-right;\n" +
                        "  text-offset: 5px, 0px;\n" +
                        "}\n" +
                        "edge {\n" +
                        "  fill-color: #999;\n" +
                        "  arrow-shape: arrow;\n" +
                        "  arrow-size: 12px, 8px;\n" +
                        "  text-alignment: above;\n" +
                        "  text-size: 14px;\n" +
                        "}";

        gs.setAttribute("ui.stylesheet", styleSheet);

        // Añadir nodos con una posición inicial en círculo
        int totalVertices = Math.max(1, graph.vertexSet().size());
        double radius = Math.max(80, totalVertices * 12.0);
        int index = 0;

        for (Direccion d : graph.vertexSet()) {
            Node node = gs.addNode(d.getCalle());
            node.setAttribute("ui.label", d.getCalle());

            double angle = (2 * Math.PI * index) / totalVertices;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            node.setAttribute("xyz", x, y, 0);
            index++;
        }

        // Añadir aristas con peso
        for (DefaultWeightedEdge e : graph.edgeSet()) {
            String src = graph.getEdgeSource(e).getCalle();
            String tgt = graph.getEdgeTarget(e).getCalle();

            String id = src + "->" + tgt;
            var edge = gs.addEdge(id, src, tgt, false); // false = no dirigido
            double peso = graph.getEdgeWeight(e);

            if (Math.floor(peso) == peso) {
                edge.setAttribute("ui.label", String.valueOf((long) peso));
            } else {
                edge.setAttribute("ui.label", String.format("%.2f", peso));
            }
        }

        // Mostrar ventana
        graphStreamViewer = gs.display();
        graphStreamViewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
        graphStreamViewer.enableAutoLayout(new SpringBox(false));
        var camera = graphStreamViewer.getDefaultView().getCamera();
        camera.setAutoFitView(true);
        camera.resetView();
    }
}
