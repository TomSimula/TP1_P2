package groupe.camembert;


import groupe.camembert.UI.CLI;

import java.io.IOException;

public class Main {
    private static final CLI cli = new CLI();
    public static void main(String[] args) throws IOException {
        cli.run();
    }
}
