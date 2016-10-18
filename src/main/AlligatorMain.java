package main;

import integration.Integration;
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

		// Generating facts from the AML files, they are converted into RDF
		Files2Facts filesAMLInRDF = new Files2Facts();
		try {

			filesAMLInRDF.prologFilePath();
			filesAMLInRDF.readFiles(ConfigManager.getFilePath(), ".aml");
			filesAMLInRDF.convertRdf();
			filesAMLInRDF.readFiles(ConfigManager.getFilePath(), ".ttl");
			filesAMLInRDF.generateExtensionalDB(ConfigManager.getFilePath());

			DeductiveDB deductiveDB = new DeductiveDB();
			// formats the output.txt in java objects
			deductiveDB.readWorkingDirectory();

			deductiveDB.executeKB();
			// formats the output.txt in java objects
			deductiveDB.readOutput();
			deductiveDB.consultKB();

			Integration integ = new Integration();
			integ.integrate();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
