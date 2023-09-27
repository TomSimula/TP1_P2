package groupe.camembert.Process;

import groupe.camembert.visitor.LineCounterVisitor;
import groupe.camembert.visitor.MethodDeclarationVisitor;
import groupe.camembert.visitor.PackageDeclarationVisitor;
import groupe.camembert.visitor.ClassDeclarationVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PackageDeclaration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Analyzer {
    private static final Parser parser = new Parser();
    private ArrayList<File> javaFiles;


    public Analyzer() {
        File folder = new File(parser.projectSourcePath);
        this.javaFiles = listJavaFilesForFolder(folder);
    }

    private static ArrayList<File> listJavaFilesForFolder(final File folder) {
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

    //Operations

    //1. Nombre de classes de l’application.
    public String getNbClasses() throws IOException {
        ClassDeclarationVisitor visitor = new ClassDeclarationVisitor();
        for (File fileEntry : javaFiles) {
            CompilationUnit parse = parser.parse(fileEntry);
            parse.accept(visitor);
        }

        return "Il y a " + visitor.getTypes().size() + " classes dans ce projet";
    }
    //2. Nombre de lignes de code de l’application.
    public String getNbCodeLines() throws IOException {
        LineCounterVisitor visitor = new LineCounterVisitor();
        for (File fileEntry : javaFiles) {
            CompilationUnit parse = parser.parse(fileEntry);
            parse.accept(visitor);
        }
        return "Il y a " +
                //visitor.get()  +
               " lignes dans ce projet";
    }
    //3. Nombre total de méthodes de l’application.
    public String getNbMethods() throws IOException {
        MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
        for (File fileEntry : javaFiles) {
            CompilationUnit parse = parser.parse(fileEntry);
            parse.accept(visitor);
        }
        return "Il y a " + visitor.getMethods().size() + " methodes dans ce projet";
    }
    //4. Nombre total de packages de l’application.
    public String getNbPackages() {
        return "";
    }
    //5. Nombre moyen de méthodes par classe.
    public String getMethodsAvgPerClass() throws IOException {
        ClassDeclarationVisitor classVisitor = new ClassDeclarationVisitor();
        MethodDeclarationVisitor methodVisitor = new MethodDeclarationVisitor();
        for (File fileEntry : javaFiles) {
            CompilationUnit parse = parser.parse(fileEntry);
            parse.accept(classVisitor);
            parse.accept(methodVisitor);
        }
        int avg = Math.round((float)methodVisitor.getMethods().size()/classVisitor.getTypes().size());

        return "Il y a en moyenne " + avg + " par classes dans ce porjet (arrondie au superieur)";
    }
    //6. Nombre moyen de lignes de code par méthode.
    public String getMethodAVGNbLines() {
        return "";
    }
    //7. Nombre moyen d’attributs par classe.
    public String getClassAVGAttributes(CompilationUnit parse) {
        return "";
    }
    //8. Les 10% des classes qui possèdent le plus grand nombre de méthodes.
    public String getClassesWithMostMethods() {
        return "";
    }
    //9. Les 10% des classes qui possèdent le plus grand nombre d’attributs.
    public String getClassesWithMostAttributes(CompilationUnit parse) {
        return "";
    }
    //10. Les classes qui font partie en même temps des deux catégories précédentes.
    public String getClassesWithMostAttributesAndMethods(CompilationUnit parse) {
        return "";
    }
    //11. Les classes qui possèdent plus de X méthodes (la valeur de X est donnée).
    public String getClassesWithMoreThanXMethods(int X) {
        return "";
    }
    //12. Les 10% des méthodes qui possèdent le plus grand nombre de lignes de code (par classe).
    public String getClassesWithMostLines() {
        return "";
    }
    //13. Le nombre maximal de paramètres par rapport à toutes les méthodes de l’application.
    public String getMAxParams() {
        return "";
    }
}
