package groupe.camembert.visitor;

import org.eclipse.jdt.core.dom.MethodInvocation;
import java.util.List;
import java.util.ArrayList;

public class MethodInvocationVisitor extends AbstractVisitor {
    private List<MethodInvocation> calls = new ArrayList<>();

    public boolean visit(MethodInvocation node) {
        hasVisited = true;
        calls.add(node);
        return super.visit(node);
    }

    public List<MethodInvocation> getCalls() {
        return calls;
    }

}
