package groupe.camembert.Process;

import org.eclipse.jdt.core.dom.MethodDeclaration;

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

}
