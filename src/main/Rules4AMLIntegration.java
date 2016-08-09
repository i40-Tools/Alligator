package main;

import util.ConfigManager;

public class Rules4AMLIntegration {

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
			deductiveDB.consultKB();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
