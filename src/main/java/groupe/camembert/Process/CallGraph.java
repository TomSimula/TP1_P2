package groupe.camembert.Process;

import guru.nidi.graphviz.engine.*;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallGraph {
    Map<MethodDeclaration, List<String>> nodes = new HashMap<>();
    Map<MethodDeclaration, List<MethodDeclaration>> mergedNodes = new HashMap<>();

    public void addNode(MethodDeclaration method, List<String> calledMethods) {
        nodes.put(method, calledMethods);
    }


    public Map<MethodDeclaration, List<String>> getNodes() {
        return nodes;
    }

    public CallGraph merge(CallGraph sousGraphe) {
        nodes.putAll(sousGraphe.getNodes());

        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CallGraph{");
        for (Map.Entry<MethodDeclaration, List<String>> entry : nodes.entrySet()) {
            sb.append("\n\t");
            sb.append(entry.getKey().resolveBinding().getDeclaringClass().getName());
            sb.append(".");
            sb.append(entry.getKey().getName());
            sb.append(" -> ");
            sb.append(entry.getValue());
        }
        sb.append("\n}");
        return sb.toString();
    }


    public MutableGraph toMutableGraph(String name) throws IOException {
        MutableGraph graph = Factory.mutGraph("callGraph").setDirected(true);
        for (Map.Entry<MethodDeclaration, List<String>> entry : nodes.entrySet()) {
            String caller = entry.getKey().resolveBinding().getDeclaringClass().getName() + "." + entry.getKey().getName();
            graph.add(Factory.mutNode(caller));
            for(String calledMethod : entry.getValue()){
                graph.add(Factory.mutNode(calledMethod));
                graph.add(Factory.mutNode(caller).addLink(Factory.mutNode(calledMethod)));
            }
        }

        Graphviz viz = Graphviz.fromGraph(graph);

        viz.render(Format.SVG).toFile(new File("Graphs/"+name+".svg"));
        viz.rasterize(Rasterizer.builtIn("pdf")).toFile(new File("Graphs/"+name+".pdf"));
        return graph;
    }

}
