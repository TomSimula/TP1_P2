package groupe.camembert.visitor;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import java.util.ArrayList;
import java.util.List;

public class MethodInvocationVisitor extends AbstractVisitor {
    List<MethodInvocation> methodInvocations = new ArrayList<>();

    @Override
    public boolean visit(MethodInvocation node) {
        methodInvocations.add(node);
        return super.visit(node);
    }

    public List<MethodInvocation> getMethodInvocations() {
        List<MethodInvocation> methodInvocationsCopy = new ArrayList<>();
        for (MethodInvocation t: methodInvocations) {
            methodInvocationsCopy.add(t);
        }
        return methodInvocationsCopy;
    }


}
