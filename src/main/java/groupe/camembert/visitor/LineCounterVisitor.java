package groupe.camembert.visitor;

import java.util.ArrayList;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.List;

public class LineCounterVisitor extends ASTVisitor {
    List<TextElement> nodes = new ArrayList<TextElement>();

    public boolean visit(TextElement node) {

        System.out.println("TextElement: " + node.toString());
        nodes.add(node);
        return super.visit(node);
    }

    public List<TextElement> getLines() {
        return nodes;
    }

    public int getNodes() {
        return nodes.size();
    }


}
