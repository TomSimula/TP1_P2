package groupe.camembert;


import groupe.camembert.UI.CLI;
import groupe.camembert.UI.GUI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //new GUI("Analyze");
        new CLI().run();
    }
}
