package groupe.camembert;


import groupe.camembert.UI.CLI;
import groupe.camembert.UI.GUI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0){
            System.out.println("Add -GUI or -CLI to choose your IHM");
        } else {
            switch (args[0]){
                case "-GUI":
                    new GUI("Analyzer");
                case "-CLI":
                    new CLI().run();
            }
        }
    }
}
