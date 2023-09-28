package groupe.camembert.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;

import java.util.ArrayList;
import java.util.List;

public class AttributeDeclarationVisitor extends ASTVisitor {
    private List<FieldDeclaration> attributes = new ArrayList<>();

    public boolean visit(FieldDeclaration node) {
        attributes.add(node);
        return super.visit(node);
    }

    public List<FieldDeclaration> getAttributes() {
        System.out.println("total nb of attributes : " + attributes.size());
        return attributes;
    }
}
