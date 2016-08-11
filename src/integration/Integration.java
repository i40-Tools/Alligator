package integration;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

import main.Files2Facts;
import util.ConfigManager;

/**
 * 
 * Integrates Two AML files based on Prolog Rules.
 */
public class Integration {

	/**
	 * This method integrates the two AML files.
	 * 
	 * @throws Throwable
	 */
	public void integrate() throws Throwable {
		XmlParser xml = new XmlParser();

		Files2Facts filesAMLInRDF = new Files2Facts();

		// gets heterogeneity files in array.
		ArrayList<File> file = filesAMLInRDF.readFiles(ConfigManager.getFilePath(), ".aml");

		String contents = FileUtils.readFileToString(new File(file.get(0).getPath()), "UTF-8");

		// One of the AML file will have its contents copied as it is.
		PrintWriter prologWriter = new PrintWriter(new File(ConfigManager.getFilePath() + "/integration.aml"));
		prologWriter.println(contents);
		prologWriter.close();

		// initializing documents.

		Document seed = xml.initInput(file.get(1).getPath());
		Document integration = xml.initInput(ConfigManager.getFilePath() + "/integration.aml");

		xml.getAllNodes(seed, integration);

		// looping through the seedNode which will be compared to matching
		// elements in output.txt
		for (int i = 0; i < xml.getSeedNodes().size(); i++) {

			// not in the conflicting Element of output.txt
			if (xml.compareConflicts(i) == 0) {

				// we run our noConflicting comparision algorithm
				if (xml.compareNonConflicts(i, seed, integration) != 1) {

					// if its identified its not in integration.aml
					// We need to add non match elements to the integration
					// file.
					xml.addNonConflicts(i, seed, integration);

				}

			}
		}

		// update the integration.aml file
		xml.finalizeIntegration(integration);

		// checkNodeByValue(seed, integration);

	}

}
