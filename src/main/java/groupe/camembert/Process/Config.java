package groupe.camembert.Process;

public class Config {

    /* Daniel Config
    public static final String projectPath = "/home/e20220002252/Documents/M2/M2/913/";
    public static final String projectSourcePath = projectPath + "/CodeTest";
    public static final String jrePath = "/usr/lib/jvm/java-11-openjdk-amd64/lib/tools.jar";
    */
//    public static final String projectPath = "/home/seb/Documents/M2/913-RestructurationLogiciels/TP1";
    public static String projectSourcePath = "/home/tom/Documents/M2/913-RestructurationLogiciels/TP1/CodeTest";
    public static String jrePath = "/usr/lib/jvm/java-11-openjdk-amd64/lib/tools.jar";

    public static void setJrePath(String path){
        jrePath = path;
    }

    public static void setprojectSourcePath(String path){
        projectSourcePath = path;
    }

}
