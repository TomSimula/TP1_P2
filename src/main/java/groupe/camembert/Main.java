package groupe.camembert;


import groupe.camembert.Config.Config;
import groupe.camembert.UI.CLI;
import groupe.camembert.UI.GUI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0){
            System.out.println("Add -GUI or -CLI <project path from home> to choose your IHM");
        } else {
            switch (args[0]){
                case "-GUI":
                    new GUI("Analyzer");
                case "-CLI":
                    if (args.length != 2){
                        System.out.println("The option for CLI is: -CLI <project path from home>");
                    } else {
                        Config.projectSourcePath = args[1];
                        new CLI().run();
                    }
            }
        }
    }
}
