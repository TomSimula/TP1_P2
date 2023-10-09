package groupe.camembert.visitor;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.List;

public class MethodDeclarationVisitor extends AbstractVisitor {
	private List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();

	public boolean visit(MethodDeclaration node) {
		hasVisited = true;
		methods.add(node);

		return super.visit(node);
	}

	public List<MethodDeclaration> getMethods() {
		List<MethodDeclaration> methodsCopy = new ArrayList<>();
		for (MethodDeclaration t: methods) {
			methods.add(t);
		}
		return methodsCopy;
	}

}
