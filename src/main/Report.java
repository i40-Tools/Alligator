package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import util.ConfigManager;

/**
 * Generates report and formats outout
 * @author omar
 *
 */

public class Report {

	// run bulk report
	static void getReport(String root) throws Throwable {
		int k = 2;
		while (k <= 2) {
			int i = 4;
			while (i <= 4) {
				long startTime = System.currentTimeMillis();
				if (k == 1) {
					System.out.println(root + "M1/M1.1//Testbeds-" + i);
					ConfigManager.filePath = root + "M1/M1.1//Testbeds-" + i + "/Generated/";
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

					} catch (Exception e) {
					}

				}

				else {
					System.out.println(root + "M" + k + "/Testbeds-" + i);

					ConfigManager.filePath = root + "M" + k + "/Testbeds-" + i + "/";
					cleanUp();
					Files2Facts filesAMLInRDF = new Files2Facts();
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
		
				}
				long endTime   = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				FileWriter fw = new FileWriter(ConfigManager.getFilePath()+"Alligator/Precision/F1NoTraining.txt",true);
				fw.write("Time:" + totalTime);//appends the string to the file
			    fw.close();
				i++;
			}
			k++;
		}

	}

/**
 * Deletes rdf files
 */
	public static void cleanUp() {
		File file1 = new File(ConfigManager.getFilePath() + "plfile0.ttl");
		File file2 = new File(ConfigManager.getFilePath() + "plfile1.ttl");
		file1.delete();
		file2.delete();
		file1 = new File(ConfigManager.getFilePath() + "seed.ttl");
		file2 = new File(ConfigManager.getFilePath() + "Generated/seed.ttl");
		file1.delete();
		file2.delete();
		file1 = new File(ConfigManager.getFilePath() + "Generated/plfile0.ttl");
		file1 = new File(ConfigManager.getFilePath() + "Generated/plfile0.ttl");
		
	}

	
	
	/**
	 * This function reads the computed result from a file and outputs the
	 * Precision, Recall and Fmeasure.
	 * 
	 * @param root
	 * @throws IOException
	 */
	static void getResults() throws IOException {
		int k = 7;
		while (k <= 7) {
			int j = 1;
			String line;
			String precision = "";
			String recall = "";
			String fmeasure = "";
			String time = "";


			while (j <= 10) {
				BufferedReader br = new BufferedReader(new FileReader(
						new File(ConfigManager.getExperimentFolder() + "M" + k + "/Testbeds-"+j
								+ "/Generated/Alligator/Precision/F1NoTraining.txt")));

				while ((line = br.readLine()) != null) {
					if (line.contains("Precision :")) {
						precision += line.replace("Precision :", "") + "\n";
					}
					if (line.contains("Recall:")) {
						recall += line.replace("Recall:", "") + "\n";
					}
					if (line.contains("Fmeasure:")) {
						fmeasure += line.replace("Fmeasure:", "") + "\n";
					}
					
					if (line.contains("Time:")) {
						time += line.replace("Time:", "") + "\n";
					}
				}

				j++;
			}

			System.out.print(precision);
			//System.out.print(recall);
			//System.out.print(fmeasure);
			//System.out.print(time);

			k++;
		}
	}
	
	/**
	 * This function gets the size of the seeds in kb.
	 * 
	 * @param root
	 * @throws IOException
	 */
	static void getSize() throws IOException {
		int k = 7;
		while (k <= 7) {
			int j = 1;

			String filesize = "";
			while (j <= 10) {
				if (k == 1) {
					File f = new File(
							ConfigManager.getExperimentFolder() + "M" + k + "/M1.1/Testbeds-" + j + "/Generated/seed.aml");
					filesize += new DecimalFormat("#.#").format(((double) f.length() / 1024))
							+ "\n";
				} else {
					File f = new File(ConfigManager.getExperimentFolder() + "M" + k + "/Testbeds-" + j + "/Generated/seed.aml");
					filesize += new DecimalFormat("#.#").format(((double) f.length() / 1024))
							+ "\n";
				}
				j++;
			}
			System.out.print(filesize);
			k++;
		}
	}	
	
}
