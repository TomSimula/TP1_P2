package groupe.camembert.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.List;

public class ClassDeclarationVisitor extends AbstractVisitor {
	private List<TypeDeclaration> types = new ArrayList<TypeDeclaration>();
	private boolean hasVisited = false;

	
	public boolean visit(TypeDeclaration node) {
		if(!node.isInterface()) {
			types.add(node);
			hasVisited = true;
		}

		return super.visit(node);
	}
	
	public List<TypeDeclaration> getTypes() {
		return types;
	}

}


