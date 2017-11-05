package main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.script.ScriptException;

import org.codehaus.groovy.control.CompilationFailedException;

import groovy.lang.Script;
import groovy.util.ResourceException;
import util.ConfigManager;

/**
 * 
 * Main class of the Alligator project
 * 
 * @author Irlan
 * @author Omar
 */
public class AlligatorMain {

	public static void main(String[] args) throws Throwable {

	     // Report.getReport(ConfigManager.getExperimentFolder());
		//Report.getResults();
		//Report.getSize();
		//System.exit(0);
		// deletes old Rdf files can cause bad results.
		Report.cleanUp();
		Files2Facts filesAMLInRDF = new Files2Facts();
		try {
			Similar similar = new Similar();

			filesAMLInRDF.prologFilePath();
			filesAMLInRDF.readFiles(ConfigManager.getFilePath(), ".aml", ".opcua", ".xml");
			filesAMLInRDF.convertRdf();
			filesAMLInRDF.readFiles(ConfigManager.getFilePath(), ".ttl", ".rdf", ".owl");
			filesAMLInRDF.generateExtensionalDB(ConfigManager.getFilePath());
			similar.readFiles(ConfigManager.getFilePath(), ".ttl", ".rdf", ".owl");

			DeductiveDB deductiveDB = new DeductiveDB();
			// formats the output.txt in java objects
			deductiveDB.readWorkingDirectory();

			deductiveDB.executeKB();
			// formats the output.txt in java objects
			deductiveDB.readOutput();
			similar.generateModel(ConfigManager.getFilePath());
			similar.convertSimilar("Alligator/output.txt");
			new AlligatorMain().evaluation();
			

			// //System.exit(0);
			// integrating files
			// Integration integ = new Integration();
			// integ.integrate();

			// chec valdity
			// File file = new File(ConfigManager.getFilePath() +
			// "integration/integration.aml");
			// if (file.exists()) {
			// if (!new XSDValidator(ConfigManager.getFilePath() +
			// "integration/integration.aml").schemaValidate()) {
			// System.out.println("Repairing Structure");
			// ModelRepair.testRoundTrip(ConfigManager.getFilePath() +
			// "integration/integration.aml");
			// System.out.println("Schema Validated");

			
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * This function is a general method to execute the PSL-based approach.
	 * 
	 * @throws CompilationFailedException
	 * @throws IOException
	 * @throws ScriptException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws groovy.util.ScriptException
	 * @throws ResourceException
	 */
	public void evaluation() throws CompilationFailedException, IOException, ScriptException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, InstantiationException, ResourceException,
			groovy.util.ScriptException {
		// Needed to run the PSL rules part
		Script script = new Script() {
			@Override
			public Object run() {
				return null;
			}
		};
		try {
			script.evaluate(new File("src/evaluation/Evaluation.groovy"));
		} catch (Exception e) {

		}
	}

}
