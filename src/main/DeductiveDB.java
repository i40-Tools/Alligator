package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.jpl7.Query;

import util.ConfigManager;

/**
 * 
 * @author Irlan
 *
 */
public class DeductiveDB {

	private String extractedAttr;
	private String originalText;
	public static ArrayList<String> baseClass;
	public static ArrayList<String> attrName;

	/**
	 * Setting working directory
	 */
	public void readWorkingDirectory() {
		String path = System.getProperty("user.dir");
		File myUri = new File(path);
		path = myUri.toURI().toString().replace("file:/", "");
		Query.hasSolution("working_directory(_," + "'" + path + "')");
	}

	/**
	 * Executing the AML Datalog rules over Prolog. Executing the
	 * writePredicates method which generates the output.txt containing the
	 * conflicting elements.
	 */
	public void executeKB() {
		String evalAML;
		// Queries evalAMl.pl
		// check if run negative rules

		if (util.ConfigManager.getNegativeRules().equals("true")) {
			evalAML = "consult('resources/files/evalAML.pl')";
		} else {
			evalAML = "consult('resources/files/evalAMLnoNeg.pl')";
		}
		System.out.println(evalAML + " " + (Query.hasSolution(evalAML) ? "succeeded" : "failed"));

		// Queries eval
		System.out.println("eval" + " " + (Query.hasSolution("eval") ? "succeeded" : "failed"));

		// Queries writePredicates.
		String writeFiles = "writePredicates";
		Query.hasSolution(writeFiles);
	}

	/**
	 * Reads the output.txt for mapping the attributes to names or values so
	 * that integration can be performed. Mapping is important to identify the
	 * attributes in AML files. This extracts the attributes from Datalog format
	 * to java string objects so that query can be made on them.
	 * 
	 * @param extractedAttr
	 * @param originalText
	 * @throws IOException
	 * @throws Exception
	 */
	public void readOutput() throws IOException {
		BufferedReader br = new BufferedReader(
				new FileReader(ConfigManager.getFilePath() + "Alligator/output.txt"));
		StringBuilder sb = new StringBuilder();
		StringBuilder orignal = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			orignal.append(line);
			orignal.append(System.lineSeparator());
			String orignalLine = line;
			int a = line.indexOf('(');
			int b = line.indexOf(')');
			if (a + 1 >= 0 && b >= 0) {
				line = line.substring(a + 1, b);
			}
			String array[] = line.split(",");
			if (array.length > 0) {
				if (!(array[0].substring(array[0].length() - 1)
						.equals(array[1].substring(array[1].length() - 1))))
					if (!orignalLine.contains("diff")) {
						sb.append("aml1:truth" + removeLastChar(StringUtils.capitalize(array[0]))
								+ "," + "aml2:truth"
								+ removeLastChar(StringUtils.capitalize(array[1])) + "\n");
					} else {
						sb.append("aml1:" + removeLastChar(StringUtils.capitalize(array[0])) + ","
								+ "aml2:" + removeLastChar(StringUtils.capitalize(array[1]))
								+ "\n");
					}

			}

			// sb.append(line + ",");
			line = br.readLine();
		}
		extractedAttr = sb.toString();
		originalText = orignal.toString();
		PrintWriter prologWriter = new PrintWriter(
				new File(ConfigManager.getFilePath() + "Alligator/output.txt"));
		prologWriter.println(extractedAttr);
		prologWriter.close();
		br.close();

	}

	/**
	 * Removes last string
	 * 
	 * @param str
	 * @return
	 */
	private String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}
}
