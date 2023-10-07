package groupe.camembert.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;

import java.util.ArrayList;
import java.util.List;

public class MethodInvocationVisitor extends AbstractVisitor {
    List<MethodInvocation> methodInvocations = new ArrayList<>();

    @Override
    public boolean visit(MethodInvocation node) {
        methodInvocations.add(node);
        hasVisited = true;
        return super.visit(node);
    }

    public List<MethodInvocation> getMethodInvocations() {
        return methodInvocations;
    }
}
