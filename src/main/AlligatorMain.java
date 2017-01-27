package main;

import java.io.File;

import Test.ModelRepair;
import integration.Integration;
import integration.XSDValidator;
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

		// xsl for opcua not working
		// Krextor krextor = new Krextor();
		// krextor.convertRdf("c:/Topology.xml", "opcua", "turtle",
		// "c:/output.ttl");

		// converts opcua to RDF
		// RDFTransformer convert = new RDFTransformer();
		//
		// // give input and output
		// convert.transform("c:/Topology.xml", "c:/test.ttl");

		// automation ML part
		// Generating facts from the AML files, they are converted into RDF
		Files2Facts filesAMLInRDF = new Files2Facts();
		try {

			filesAMLInRDF.prologFilePath();
			filesAMLInRDF.readFiles(ConfigManager.getFilePath(), ".aml", ".opcua", ".xml");
			filesAMLInRDF.convertRdf();
			filesAMLInRDF.readFiles(ConfigManager.getFilePath(), ".ttl", ".rdf", ".owl");
			filesAMLInRDF.generateExtensionalDB(ConfigManager.getFilePath());

			DeductiveDB deductiveDB = new DeductiveDB();
			// formats the output.txt in java objects
			deductiveDB.readWorkingDirectory();

			deductiveDB.executeKB();
			// formats the output.txt in java objects
			deductiveDB.readOutput();
			deductiveDB.consultKB();

			// integrating files
			Integration integ = new Integration();
			integ.integrate();

			// chec valdty
			File file = new File(ConfigManager.getFilePath() + "integration/integration.aml");
			if (file.exists()) {
				if (!new XSDValidator(ConfigManager.getFilePath() + "integration/integration.aml").schemaValidate()) {
					System.out.println("Repairing Structure");
					ModelRepair.testRoundTrip(ConfigManager.getFilePath() + "integration/integration.aml");
					System.out.println("Schema Validated");

				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
