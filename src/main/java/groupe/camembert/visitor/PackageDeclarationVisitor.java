package groupe.camembert.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PackageDeclarationVisitor extends ASTVisitor {
    List<PackageDeclaration> packages = new ArrayList<>();

    public boolean visit(PackageDeclaration node){
        packages.add(node);
        return super.visit(node);
    }

    public List<Name> getPackageNames(){
        return packages.stream().map(p -> p.getName()).distinct().collect(Collectors.toList());
    }

}
