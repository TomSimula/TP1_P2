package groupe.camembert.Process;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.List;

public class CallNode {
    private final MethodDeclaration method;
    private List<String> calledMethods;

    public CallNode(MethodDeclaration method, List<String> calledMethods) {
        this.method = method;
        this.calledMethods = calledMethods;
    }

    public MethodDeclaration getMethod() {
        return method;
    }

    public List<String> getCalledMethods() {
        return calledMethods;
    }

    public void addCalledMethod(String calledMethod) {
        calledMethods.add(calledMethod);
    }


    public String toString() {
        return "\nCallNode{" +
                "method=" + method.resolveBinding().getDeclaringClass().getQualifiedName() + "." + method.getName() +
                ", calledMethods=" + calledMethods +
                "}";
    }
}
