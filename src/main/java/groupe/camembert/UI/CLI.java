package groupe.camembert.UI;


import groupe.camembert.Process.Analyzer;

import java.io.IOException;
import java.util.Scanner;

public class CLI {
    private final String menu =
            "Choisissez l'analyse à réaliser: (0 pour Quitter).\n"+
            "1. Nombre de classes.\n" +
            "2. Nombre de lignes de code.\n" +
            "3. Nombre total de méthodes.\n" +
            "4. Nombre total de packages.\n" +
            "5. Nombre moyen de méthodes par classe.\n" +
            "6. Nombre moyen de lignes de code par classe.\n" +
            "7. Nombre moyen d'attributs par classe.\n" +
            "8. Les 10% des classes qui possèdent le plus grand nombre de méthodes.\n" +
            "9. Les 10% des classes qui possèdent le plus grand nombre d’attributs.\n" +
            "10. Les classes qui font partie en même temps des deux catégories précédentes.\n" +
            "11. Les classes qui possèdent plus de X méthodes (la valeur de X est donnée).\n" +
            "12. Les 10% des méthodes qui possèdent le plus grand nombre de lignes de code (par classe).\n" +
            "13. Le nombre maximal de paramètres par rapport à toutes les méthodes de l’application.\n" +
            ""
    ;

private final Analyzer analyzer = new Analyzer();
private final Scanner scanner = new Scanner(System.in);

    public void run() throws IOException {
        int option = -1;
        String res = "";
        while(option != 0){
            System.out.println(menu);
            option = scanner.nextInt();
            switch(option) {
                case 1:
                    res = "Il y a " + analyzer.getNbClasses() + " classes dans ce projet\n";
                    break;
                case 2:
                    res = "Il y a " + analyzer.getNbCodeLines() + " lignes dans ce projet\n";
                    break;
                case 3:
                    res = "Il y a " + analyzer.getNbMethods() + " methodes dans ce projet\n";
                    break;
                case 4:
                    res = "Il y a " + analyzer.getNbPackages() + " packages dans ce projet\n";
                    break;
                case 5:
                    res = "Il y a en moyenne " + analyzer.getMethodsAvgPerClass() + " methodes par classe dans ce projet (arrondi au superieur)";
                    break;
                case 6:
                    res = "Il y a en moyenne " + analyzer.getMethodAVGNbLines() + " lignes de code par methode dans ce projet (arrondi au superieur)\n";
                    break;
                case 7:
                    res = "Il y a en moyenne " + analyzer.getAttributeAvgPerClass() + " attribut par classes dans ce projet (arrondi au superieur)\n";
                    break;
                case 8:
                    res = analyzer.getClassesWithMostMethods();
                    break;
                case 9:
                    res = analyzer.getClassesWithMostAttributes();
                    break;
                case 10:
                    res = analyzer.getClassesWithMostAttributesAndMethods();
                    break;
                case 11:
                    System.out.println("Entrez le nombre de méthodes minimum");
                    int nb = scanner.nextInt();
                    res = analyzer.getClassesWithMoreThanXMethods(nb);
                    break;
                case 12:
                    res = analyzer.getMethodsWithMostLines();
                    break;
                case 13:
                    res = "Le nombre maximal de paramètres par rapport à toutes les méthodes de l’application est de " + analyzer.getMaxParams() + "\n";
                    break;
                default:
                    break;


            }
            System.out.println(res);
        }
    }
}
