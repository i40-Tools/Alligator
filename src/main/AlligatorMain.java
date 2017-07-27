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
		
     	getReport("C:/Users/omar/Desktop/examples/run -1/");

		System.exit(0);
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

			formatOuput();
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
	
// run bulk report
	static void getReport(String root) throws Throwable {
		int k = 1;
		while (k <=1) {
			int i = 3;
			while (i <=10) {
				if (k == 1) {
					System.out.println(root + "M1/M1.1//Testbeds-" + i);
					ConfigManager.filePath = root + "M1/M1.1//Testbeds-"+i+"/Generated/" ;
					Files2Facts filesAMLInRDF = new Files2Facts();
					try{

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

						formatOuput();
					}catch(Exception e){
						formatOuput();
					}

				}

				else {
					System.out.println(root + "M" + k + "/Testbeds-" + i);
					
					ConfigManager.filePath = root + "M" + k + "/Testbeds-" + i + "/Generated/";

					Files2Facts filesAMLInRDF = new Files2Facts();
					

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

					formatOuput();
				
				}
				i++;
			}
			k++;
		}

//		getresults(root);
	}

	
	
	private static void formatOuput() throws IOException {
		// TODO Auto-generated method stub

		File file = new File(ConfigManager.getFilePath() + "output.txt");

		String name[] = {"sameRoleClassLib","sameRoleClass","sameInterfaceClassLib","sameInterfaceClass",
				 "sameSystemUnitClassLib","sameSystemUnitClass","sameInstanceHierarichy",
				 "sameAttribute","sameIdentifier"};
		
		String diffname[] = {"diffAttribute","diffIdentifier","diffIdentifier2",
				"diffRoleClass","diffRoleClassLib","diffSystemUnitClass","diffSystemUnitClassLib",
				 "diffInterfaceClass","diffInterfaceClassLib","diffInstanceHierarichy"};

		ArrayList<String> aml1 = new ArrayList<>();
		ArrayList<String> list=new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;

			while ((line = br.readLine()) != null) {
              if(!list.contains(line))				
			     list.add(line);
			}
		}
		
		PrintWriter writer = new PrintWriter(file); 
		for(String str: list) {
		  writer.println(str);
		}
		writer.close();

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
						line = line.replace("äö)", "");
						line = line.replace("äö1)", "");
						line = line.replace("äö2)", "");
						line = line.replace("äö3)", "");
						line = line.replace("äö4)", "");						
						line = line.replace("äö1", "äö");
						line = line.replace("äö2", "äö");							
						line = line.replace("äö3", "äö");
						line = line.replace("äö4", "äö");							
						line = line.replace(".", "");
						line = line.replaceAll(name[i], "");
						String t[] = line.split("äö,");
						if(t!=null)
						if (!aml1.contains("aml1:" + t[0]+ "\t" + "aml2:" + t[1]+ "\t" + "1")) {
							aml1.add("aml1:" + t[0]+ "\t" + "aml2:" + t[1]+ "\t" + "1");
						}

						break;
					}
				}
				
				
				for (int i = 0; i < diffname.length; i++) {
					if (line.contains(diffname[i])) {

						line = line.replaceAll("'1,", ",");
						line = line.replaceAll("'2,", ",");
						line = line.replaceAll("'1", "");
						line = line.replaceAll("'2", "");
						line = line.replaceAll("'", "");
						line = line.replace("(", "");
						line = line.replace("äö)", "");
						line = line.replace("äö1)", "");
						line = line.replace("äö2)", "");
						line = line.replace("äö3)", "");
						line = line.replace("äö4)", "");						
						line = line.replace("äö1", "äö");
						line = line.replace("äö2", "äö");							
						line = line.replace("äö3", "äö");
						line = line.replace("äö4", "äö");							

						line = line.replace(".", "");
						line = line.replaceAll(diffname[i], "");

						String t[] = line.split("äö,");
						if(t!=null){

						if (!aml1.contains("aml1:" + t[0]+ "\t" + "aml2:" + t[1]+ "\t" + "0")) {
							aml1.add("aml1:" + t[0]+ "\t" + "aml2:" + t[1]+ "\t" + "0");
						}
						}

						break;
					}
				}

			}

			PrintWriter writers = new PrintWriter(file);
			for (int i = 0; i < aml1.size(); i++) {

				writers.println(aml1.get(i));
			}
			writers.close();

		}

	}

}
