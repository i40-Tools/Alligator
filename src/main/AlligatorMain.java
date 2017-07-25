package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

			//formatOuput();
			System.exit(0);

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

	private static void formatOuput() throws IOException {
		// TODO Auto-generated method stub

		File file = new File(ConfigManager.getFilePath() + "output.txt");

		String name[] = { "refSemantic", "sameEClassSpec", "hasRoleClassLib", "hasRoleClass",
				"roleClassRefSem", "classificationClass", "sameClassification", "sameEClassVersion",
				"sameEClassIRDI", "sameAttribute", "sameRoleClass", "sameRoleClassLib",
				"sameCAEXFile", "hasAttribute", "sameAttributeRoleClass",
				"hasCorrespondingAttributePath", "sameRefSemantic", "hasRefSemantic",
				"hasAttributeName", "hasAttributeValue", "type", "eClassClassificationAtt",
				"eClassVersionAtt", "eClassIRDIAtt", "sameInterfaceClass", "type",
				"sameEClassificationRoleClass", "sameRoleClassLib", "sameSystemUnitClass",
				"sibling", "concatString", "identifier", "sameIdentifier", "sameId",
				"hasInternalElement", "hasInternalLink", "hasRefPartnerSideA",
				"hasRefPartnerSideB" ,"diffAttribute"};
		String result = "";
		ArrayList<String> aml1 = new ArrayList<>();
		ArrayList<String> aml2 = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				// process the line.

				for (int i = 0; i < name.length; i++) {
					if (line.contains(name[i])) {

						line = line.replaceAll("'1,", ",");
						line = line.replaceAll("'2,", ",");
						line = line.replaceAll("'1", "");
						line = line.replaceAll("'2", "");
						line = line.replaceAll("'", "");
						line = line.replace("(", "");
						line = line.replace(")", "");
						line = line.replace(".", "");
						line = line.replaceAll(name[i], "");
						String t[] = line.split(",");
						if (!aml1.contains("aml1:" + t[0])) {
							aml1.add("aml1:" + t[0]);
						}
						if (!aml2.contains("aml2:" + t[1])) {
							aml2.add("aml2:" + t[1]);
						}

						result += line.replaceAll(name[i], "") + "\n";

						break;
					}
				}

			}

			PrintWriter writer = new PrintWriter(file);
			for (int i = 0; i < aml1.size(); i++) {

				writer.println(aml1.get(i) + "\t" + aml2.get(i) + "\t" + "1");
			}
			writer.close();

		}

	}

}
