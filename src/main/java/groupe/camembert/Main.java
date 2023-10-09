package groupe.camembert;


import groupe.camembert.UI.CLI;
import groupe.camembert.UI.GUI;

import java.io.IOException;

public class Main {
    private static final CLI cli = new CLI();
    private static final GUI gui = new GUI("Camembert");
    public static void main(String[] args) throws IOException {
//        gui.run(null);
        cli.run();
    }
}
