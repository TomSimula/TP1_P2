package groupe.camembert;


import groupe.camembert.UI.CLI;
import groupe.camembert.UI.GUI;

import java.io.IOException;

public class Main {
    private static final CLI cli = new CLI();
    public static void main(String[] args) throws IOException {
        new GUI("Analyze");
       // cli.run();
    }
}
