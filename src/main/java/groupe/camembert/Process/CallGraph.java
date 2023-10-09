package groupe.camembert.Process;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import org.eclipse.jdt.core.dom.MethodDeclaration;

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


    public MutableGraph toMutableGraph() throws IOException {
        MutableGraph graph = guru.nidi.graphviz.model.Factory.mutGraph("callGraph").setDirected(true);
        for (Map.Entry<MethodDeclaration, List<String>> entry : nodes.entrySet()) {
            String caller = entry.getKey().resolveBinding().getDeclaringClass().getName() + "." + entry.getKey().getName();
            graph.add(guru.nidi.graphviz.model.Factory.mutNode(caller));
            for(String calledMethod : entry.getValue()){
                graph.add(guru.nidi.graphviz.model.Factory.mutNode(calledMethod));
                graph.add(guru.nidi.graphviz.model.Factory.mutNode(caller).addLink(guru.nidi.graphviz.model.Factory.mutNode(calledMethod)));
            }
        }

        /*Graphviz.useEngine(new GraphvizCmdLineEngine()); // Rasterizer.builtIn() works only with CmdLineEngine
        Graph g = graph("example5").directed().with(node("abc").link(node("xyz")));
        Graphviz viz = Graphviz.fromGraph(g);
        viz.width(200).render(Format.SVG).toFile(new File("example/ex5.svg"));
        viz.width(200).rasterize(Rasterizer.BATIK).toFile(new File("example/ex5b.png"));
        viz.width(200).rasterize(Rasterizer.SALAMANDER).toFile(new File("example/ex5s.png"));
        viz.width(200).rasterize(Rasterizer.builtIn("pdf")).toFile(new File("example/ex5p"));
        String dot = viz.render(Format.DOT).toString();
        String json = viz.engine(Engine.NEATO).render(Format.JSON).toString();
        BufferedImage image = viz.render(Format.PNG).toImage();*/
        Graphviz.fromGraph(graph).render(Format.PNG).toFile(new File("example/ex1m.png"));
        return graph;
    }

}
