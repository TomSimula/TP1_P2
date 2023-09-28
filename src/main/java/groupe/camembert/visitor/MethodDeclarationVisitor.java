package groupe.camembert.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;

public class MethodDeclarationVisitor extends AbstractVisitor {
	private List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
	private boolean hasVisited = false;
	
	public boolean visit(MethodDeclaration node) {
		hasVisited = true;
		methods.add(node);
		return super.visit(node);
	}
	
	public List<MethodDeclaration> getMethods() {
		return methods;
	}
}
