package groupe.camembert;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.List;

public class CallNode {
    private final MethodDeclaration method;
    private List<CallNode> calledMethods;

    public CallNode(MethodDeclaration method, List<CallNode> calledMethods) {
        this.method = method;
        this.calledMethods = calledMethods;
    }

    public MethodDeclaration getMethod() {
        return method;
    }

    public List<CallNode> getCalledMethods() {
        return calledMethods;
    }

    public void addCalledMethod(CallNode calledMethod) {
    	calledMethods.add(calledMethod);
    }

}
