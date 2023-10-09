package groupe.camembert.Process;

public class Config {

    public static String projectSourcePath =  "/home/e20220002252/Documents/M2/M2/913/CodeTest";
    public static String jrePath = System.getProperty("java.home");

    public static void setProjectSourcePath(String path){
        projectSourcePath = path;
    }

    public static void setJrePath(String path){
        jrePath = path;
    }

}
