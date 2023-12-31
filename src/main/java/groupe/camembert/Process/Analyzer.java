package groupe.camembert.Process;

import groupe.camembert.Config.Config;
import groupe.camembert.visitor.*;
import org.eclipse.jdt.core.dom.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class Analyzer {
    private static final Parser parser = new Parser();
    private ArrayList<File> javaFiles;

    private HashMap<String, ASTVisitor> visitors = new HashMap<>();


    public Analyzer() {
        File folder = new File(Config.projectSourcePath);
        this.javaFiles = listJavaFilesForFolder(folder);
    }
    //Operations

    //1. Nombre de classes de l’application.
    public int getNbClasses() throws IOException {
        ClassDeclarationVisitor visitor = (ClassDeclarationVisitor) getVisitor("class");
        visitProject(visitor);

        return visitor.getTypes().size();
    }

    //2. Nombre de lignes de code de l’application.
    public int getNbCodeLines() throws IOException {
        int LineCpt = 0;
        for (File fileEntry : javaFiles) {
            CompilationUnit parse = parser.parse(fileEntry);
            LineCpt += parse.getLineNumber(parse.getExtendedLength(parse)-1);
        }
        return LineCpt;
    }

    //3. Nombre total de méthodes de l’application.
    public int getNbMethods() throws IOException {
        ClassDeclarationVisitor visitor = (ClassDeclarationVisitor) getVisitor("class");
        visitProject(visitor);
        int nbMethods = visitor.getTypes().stream()
                .map(typeDeclaration -> typeDeclaration.getMethods().length)
                .reduce(0, Integer::sum);

        return nbMethods;
    }

    //4. Nombre total de packages de l’application.
    public int getNbPackages() throws IOException{
        PackageDeclarationVisitor visitor = (PackageDeclarationVisitor) getVisitor("package");
        visitProject(visitor);

        return visitor.getPackageNames().size();
    }

    //5. Nombre moyen de méthodes par classe.
    public int getMethodsAvgPerClass() throws IOException {
        return Math.round((float)getNbMethods()/getNbClasses());
    }


    //6. Nombre moyen de lignes de code par méthode.
    public int getLinesAVGPerMethod() throws IOException {
        MethodDeclarationVisitor methodVisitor = (MethodDeclarationVisitor) getVisitor("method");

        int sumLines = 0;
        int nbMethods = 0;

        visitProject(methodVisitor);

        //pas de Map car parfois methodes sans corps

        for(MethodDeclaration method : methodVisitor.getMethods()){
            if (method.getBody() != null){
                sumLines += method.getBody().toString().split("\n").length;
                //On compte pas les { }
                sumLines -= 2;
            }
            nbMethods++; //on compte les methodes vides aussi du coup ?
        }
        int avg = Math.round((float)sumLines/nbMethods);
        return avg;
    }


    //7. Nombre moyen d’attributs par classe.
    public int getAttributeAvgPerClass() throws IOException{
        ClassDeclarationVisitor visitor = (ClassDeclarationVisitor) getVisitor("class");
        visitProject(visitor);
        int nbAttributes = visitor.getTypes().stream()
                .map(typeDeclaration -> typeDeclaration.getFields().length)
                .reduce(0, Integer::sum);
        int nbClasses = visitor.getTypes().size();
        int avg = Math.round((float)nbAttributes/nbClasses);

        return avg;
    }

    //8. Les 10% des classes qui possèdent le plus grand nombre de méthodes.
    public List<TypeDeclaration> getClassesWithMostMethods() throws IOException {
        ClassDeclarationVisitor visitor = (ClassDeclarationVisitor) getVisitor("class");
        visitProject(visitor);
        List<TypeDeclaration> types = visitor.getTypes();
        Collections.sort(types, Comparator.comparing(o -> o.getName().toString()));
        types.sort(Comparator.comparingInt(o ->o.getMethods().length));
        Collections.reverse(types);

        int tenPercent = Math.round((float)types.size()/10);
        List<TypeDeclaration> tenPercentTypes = types.subList(0, tenPercent);
        return tenPercentTypes;
    }


    //9. Les 10% des classes qui possèdent le plus grand nombre d’attributs.
    public List<TypeDeclaration> getClassesWithMostAttributes() throws IOException{
        ClassDeclarationVisitor visitor = (ClassDeclarationVisitor) getVisitor("class");
        visitProject(visitor);
        List<TypeDeclaration> types = visitor.getTypes();
        Collections.sort(types, Comparator.comparing(o -> o.getName().toString()));
        types.sort(Comparator.comparingInt(o -> o.getFields().length));
        Collections.reverse(types);
        int tenPercent = Math.round((float)types.size()/10);
        List<TypeDeclaration> tenPercentTypes = types.subList(0, tenPercent);
        return tenPercentTypes;
    }



    //10. Les classes qui font partie en même temps des deux catégories précédentes.
    public List<TypeDeclaration> getClassesWithMostAttributesAndMethods() throws IOException {

        List<TypeDeclaration> l1 = new ArrayList<>();
        for (TypeDeclaration t:getClassesWithMostAttributes()) {
            l1.add(t);
        }
        List<TypeDeclaration> l2 = new ArrayList<>();
        for (TypeDeclaration t:getClassesWithMostMethods()) {
            l2.add(t);
        }
        l1.retainAll(l2);

        return l1;
    }


    //11. Les classes qui possèdent plus de X méthodes (la valeur de X est donnée).
    public List<TypeDeclaration> getClassesWithMoreThanXMethods(int x) throws IOException{
        ClassDeclarationVisitor visitor = (ClassDeclarationVisitor) getVisitor("class");
        visitProject(visitor);
        List<TypeDeclaration> types = visitor.getTypes();
        types.sort(Comparator.comparingInt(o -> o.getMethods().length));
        types.removeIf(typeDeclaration -> typeDeclaration.getMethods().length <= x);
        return types;
    }


    //12. Les 10% des méthodes qui possèdent le plus grand nombre de lignes de code (par classe).
    public String getMethodsWithMostLines() throws IOException {

        ClassDeclarationVisitor visitor = (ClassDeclarationVisitor) getVisitor("class");
        visitProject(visitor);
        List<TypeDeclaration> types = visitor.getTypes();

        StringBuilder sb = new StringBuilder();
        for (TypeDeclaration type : types) {
            List<MethodDeclaration> methods = new ArrayList<>(Arrays.asList(type.getMethods()));

            methods.sort(Comparator.comparingInt(o -> {
                if (o.getBody() == null) return 0;
                else return o.getBody().toString().split("\n").length;
            }));
            Collections.reverse(methods);
            int tenPercent = (int) Math.ceil((float) methods.size() / 10);
            List<MethodDeclaration> tenPercentMethods = methods.subList(0, tenPercent);
            tenPercentMethods.removeIf(methodDeclaration -> methodDeclaration.getBody() == null);
            if (tenPercentMethods.size() > 0) {
                sb.append("\n-Plus grande(s) Methode(s) de la classe ")
                        .append(type.getName().getFullyQualifiedName())
                        .append(" : \n");
                for (MethodDeclaration method : tenPercentMethods) {
                    sb.append(method.getName().getFullyQualifiedName())
                            .append(": ")
                            .append(method.getBody().toString().split("\n").length)
                            .append(" lignes\n");
                }
            }
        }
        return sb.toString() + "\n";
    }


    //13. Le nombre maximal de paramètres par rapport à toutes les méthodes de l’application.
    public int getMaxParams() throws IOException {
        MethodDeclarationVisitor visitor = (MethodDeclarationVisitor) getVisitor("method");
        visitProject(visitor);
        List<MethodDeclaration> methods = visitor.getMethods();
        int max = 0;
        for(MethodDeclaration method : methods){
            int nbParams = method.parameters().size();
            if(nbParams > max){
                max = nbParams;
            }
        }
        return max;
    }

    //Merge every call graph of each class
    public CallGraph buildCallGraph() throws IOException {
        ClassDeclarationVisitor visitor = (ClassDeclarationVisitor) getVisitor("class");
        visitProject(visitor);
        CallGraph graph = new CallGraph();

        for(TypeDeclaration clazz: visitor.getTypes()) {
            CallGraph sousGraph = buildClassCallGraph(clazz);
            graph = graph.merge(sousGraph);
        }

        //graph.toMutableGraph();
        return graph;
    }

    //Create and return the call graph of a class
    private CallGraph buildClassCallGraph(TypeDeclaration clazz){
        CallGraph graph = new CallGraph();
        for(MethodDeclaration method: clazz.getMethods()) {
            MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor();
            method.accept(methodInvocationVisitor);
            List<MethodInvocation> methodInvocations = methodInvocationVisitor.getMethodInvocations();
            List<String> calledMethods = new ArrayList<>();
            for(MethodInvocation methodInvocation: methodInvocations) {
                Expression expression = methodInvocation.getExpression();
                ITypeBinding typeBinding;
                if(expression == null) {
                    typeBinding = clazz.resolveBinding();
                } else {
                    typeBinding = expression.resolveTypeBinding();
                }
                if(typeBinding != null) {
                    String calleeFullName = typeBinding.getName();
                    calledMethods.add(calleeFullName + "." + methodInvocation.getName().getIdentifier());
                } else {
                    calledMethods.add(methodInvocation.getName().toString());
                }
            }
            graph.addNode(method, calledMethods);
        }
        return graph;

    }

    //Find and return all file in a directory
    private ArrayList<File> listJavaFilesForFolder(final File folder) {
        ArrayList<File> javaFiles = new ArrayList<File>();
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                javaFiles.addAll(listJavaFilesForFolder(fileEntry));
            } else if (fileEntry.getName().contains(".java")) {
                javaFiles.add(fileEntry);
            }
        }

        return javaFiles;
    }

    //Check if we already have the desired visitor and return it if yes otherwise return a new one
    private ASTVisitor getVisitor(String type) {
        ASTVisitor visitor = visitors.get(type);
        if(visitor == null){
            switch (type){
                case "class":
                    visitor = new ClassDeclarationVisitor();
                    break;
                case "attribute":
                    visitor = new AttributeDeclarationVisitor();
                    break;
                case "method":
                    visitor = new MethodDeclarationVisitor();
                    break;
                case "package":
                    visitor = new PackageDeclarationVisitor();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
            visitors.put(type, visitor);
        }
        return visitor;
    }

    //Visit every file with a visitor
    private void visitProject(AbstractVisitor visitor) throws IOException {
        if(!visitor.hasVisited()){
            for (File fileEntry : javaFiles) {
                CompilationUnit parse = parser.parse(fileEntry);
                parse.accept(visitor);
            }
        }
    }

}
