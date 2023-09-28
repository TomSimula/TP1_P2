package groupe.camembert.visitor;

import java.util.ArrayList;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.List;

public class LineCounterVisitor extends ASTVisitor {
    List<ASTNode> nodes = new ArrayList<ASTNode>();


    public boolean visit(TypeDeclaration node) {
        nodes.add(node);
        return super.visit(node);
    }

    public List<ASTNode> getLines() {
        return nodes;
    }


}
