package groupe.camembert.Process;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Parser {

	public static final String projectPath = "/home/tom/Documents/M2/913-RestructurationLogiciels/TP1";
	public static final String projectSourcePath = projectPath + "/CodeTest";
	public static final String jrePath = "/usr/lib/jvm/java-11-openjdk-amd64/lib/tools.jar";
	public static CompilationUnit parse(File fileEntry) throws IOException {
		ASTParser parser = ASTParser.newParser(AST.JLS4); // java +1.6
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		parser.setBindingsRecovery(true);
 
		Map options = JavaCore.getOptions();
		parser.setCompilerOptions(options);
 
		parser.setUnitName("");
 
		String[] sources = { projectSourcePath }; 
		String[] classpath = {jrePath};
 
		parser.setEnvironment(classpath, sources, new String[] { "UTF-8"}, true);
		parser.setSource(FileUtils.readFileToString(fileEntry).toCharArray());
		
		return (CompilationUnit) parser.createAST(null); // create and parse
	}

}
