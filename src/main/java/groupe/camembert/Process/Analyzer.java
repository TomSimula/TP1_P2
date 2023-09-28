package groupe.camembert.Process;

import groupe.camembert.visitor.*;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.io.File;
import java.io.IOException;
import java.util.*;


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
    public String getNbClasses() throws IOException {
        ClassDeclarationVisitor visitor = (ClassDeclarationVisitor) getVisitor("class");
        visitFile(visitor);
        return "Il y a " + visitor.getTypes().size() + " classes dans ce projet";
    }

    //2. Nombre de lignes de code de l’application.
    public String getNbCodeLines() throws IOException {
        //LineCounterVisitor visitor = new LineCounterVisitor();
        int LineCpt = 0;
        for (File fileEntry : javaFiles) {
            CompilationUnit parse = parser.parse(fileEntry);
            LineCpt += parse.getLineNumber(parse.getExtendedLength(parse)-1);
        }
        return "Il y a " + LineCpt + " lignes dans ce projet";
    }

    //3. Nombre total de méthodes de l’application.
    public String getNbMethods() throws IOException {
        MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
        visitFile(visitor);
        return "Il y a " + visitor.getMethods().size() + " methodes dans ce projet";
    }

    //4. Nombre total de packages de l’application.
    public String getNbPackages() throws IOException{
        PackageDeclarationVisitor visitor = new PackageDeclarationVisitor();
        visitFile(visitor);
        return "Il y a " + visitor.getPackageNames().size() + " packages dans ce projet";
    }

    //5. Nombre moyen de méthodes par classe.
    public String getMethodsAvgPerClass() throws IOException {
        ClassDeclarationVisitor visitor = (ClassDeclarationVisitor) getVisitor("class");
        visitFile(visitor);
        int nbAttributes = visitor.getTypes().stream()
                .map(typeDeclaration -> typeDeclaration.getMethods().length)
                .reduce(0, Integer::sum);
        int nbClasses = visitor.getTypes().size();
        int avg = Math.round((float)nbAttributes/nbClasses);

        return "Il y a en moyenne " + avg + " par classes dans ce porjet (arrondie au superieur)";
    }


    //6. Nombre moyen de lignes de code par méthode.
    public String getMethodAVGNbLines() throws IOException {
        MethodDeclarationVisitor methodVisitor = new MethodDeclarationVisitor();

        int avg = 0;

        for (File fileEntry : javaFiles) {
            CompilationUnit parse = parser.parse(fileEntry);
            parse.accept(methodVisitor);
        }

        for (MethodDeclaration m : methodVisitor.getMethods())
        {
            //TODO
        }

        return "Il y a en moyenne " + avg + " lignes de code par methodes dans ce projet (arrondi au superieur)";
    }


    //7. Nombre moyen d’attributs par classe.
    public String getClassAVGAttributes() throws IOException{
        ClassDeclarationVisitor visitor = (ClassDeclarationVisitor) getVisitor("class");
        visitFile(visitor);
        int nbAttributes = visitor.getTypes().stream()
                .map(typeDeclaration -> typeDeclaration.getFields().length)
                .reduce(0, Integer::sum);
        int nbClasses = visitor.getTypes().size();
        int avg = Math.round((float)nbAttributes/nbClasses);

        return "Il y a en moyenne " + avg + " attribut par classes dans ce porjet (arrondie au superieur)";
    }

    //8. Les 10% des classes qui possèdent le plus grand nombre de méthodes.
    public String getClassesWithMostMethods() throws IOException {
        ClassDeclarationVisitor visitor = (ClassDeclarationVisitor) getVisitor("class");
        visitFile(visitor);
        List<TypeDeclaration> types = visitor.getTypes();
        types.sort(Comparator.comparingInt(o -> o.getMethods().length));
        Collections.reverse(types);
        int tenPercent = Math.round((float)types.size()/10);
        List<TypeDeclaration> tenPercentTypes = types.subList(0, tenPercent);
        StringBuilder sb = new StringBuilder();
        for(TypeDeclaration type : tenPercentTypes){
            sb.append(type.getName().getFullyQualifiedName()).append(": ").append(type.getMethods().length).append("\n");
        }
        return sb.toString();
    }


    //9. Les 10% des classes qui possèdent le plus grand nombre d’attributs.
    public String getClassesWithMostAttributes() throws IOException{
        ClassDeclarationVisitor visitor = (ClassDeclarationVisitor) getVisitor("class");
        visitFile(visitor);
        List<TypeDeclaration> types = visitor.getTypes();
        types.sort(Comparator.comparingInt(o -> o.getFields().length));
        Collections.reverse(types);
        int tenPercent = Math.round((float)types.size()/10);
        List<TypeDeclaration> tenPercentTypes = types.subList(0, tenPercent);
        StringBuilder sb = new StringBuilder();
        for(TypeDeclaration type : tenPercentTypes){
            sb.append(type.getName().getFullyQualifiedName()).append(": ").append(type.getFields().length).append("\n");
        }
        return sb.toString();
    }



    //10. Les classes qui font partie en même temps des deux catégories précédentes.
    public String getClassesWithMostAttributesAndMethods() throws IOException {
        return "";
    }


    //11. Les classes qui possèdent plus de X méthodes (la valeur de X est donnée).
    public String getClassesWithMoreThanXMethods(int x) throws IOException{
        ClassDeclarationVisitor visitor = (ClassDeclarationVisitor) getVisitor("class");
        visitFile(visitor);
        List<TypeDeclaration> types = visitor.getTypes();
        types.sort(Comparator.comparingInt(o -> o.getMethods().length));
        types.removeIf(typeDeclaration -> typeDeclaration.getMethods().length <= x);
        StringBuilder sb = new StringBuilder();
        for(TypeDeclaration type : types){
            sb.append(type.getName().getFullyQualifiedName())
                    .append(": ")
                    .append(type.getMethods().length)
                    .append("\n");
        }
        return sb.toString();
    }


    //12. Les 10% des méthodes qui possèdent le plus grand nombre de lignes de code (par classe).
    public String getClassesWithMostLines() {
        return "";
    }


    //13. Le nombre maximal de paramètres par rapport à toutes les méthodes de l’application.
    public String getMaxParams() throws IOException {
        MethodDeclarationVisitor visitor = (MethodDeclarationVisitor) getVisitor("method");
        visitFile(visitor);
        List<MethodDeclaration> methods = visitor.getMethods();
        int max = 0;
        for(MethodDeclaration method : methods){
            int nbParams = method.parameters().size();
            if(nbParams > max){
                max = nbParams;
            }
        }
        return "Le nombre maximal de paramètres par rapport à toutes les méthodes de l’application est de " + max;
    }

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

    private void visitFile(AbstractVisitor visitor) throws IOException {
        if(!visitor.hasVisited()){
            for (File fileEntry : javaFiles) {
                CompilationUnit parse = parser.parse(fileEntry);
                parse.accept(visitor);
            }
        }
    }

}
