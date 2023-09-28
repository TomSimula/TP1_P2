package groupe.camembert.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;

public abstract class AbstractVisitor extends ASTVisitor {
    private boolean hasVisited;
    public boolean hasVisited() {
        return hasVisited;
    }
}
