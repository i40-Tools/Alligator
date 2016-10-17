package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import main.Files2Facts;
import util.ConfigManager;

public class TestsFiles2Facts {

	static Files2Facts filesAMLInRDF;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		filesAMLInRDF = new Files2Facts();
	}

	@Test
	public void rdfFilesShouldParse() {

		try {
			equals(filesAMLInRDF.factsFromFiles(
					new File("C://HeterogeneityExampleData//AutomationML//M2-Granularity//Testbeds-1//plfile0.ttl"),
					0));
			equals(filesAMLInRDF.factsFromFiles(
					new File("C://HeterogeneityExampleData//AutomationML//M2-Granularity//Testbeds-1//plfile1.ttl"),
					1));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void configFileShouldRead() {
		assertEquals("C:/HeterogeneityExampleData/AutomationML/M2-Granularity/Testbeds-1/",
				ConfigManager.getFilePath());

	}

	// Read aml Files and convert it to RDF
	@Test
	public void amlfilesShouldRead() {

		try {
			filesAMLInRDF.readFiles(ConfigManager.getFilePath(), ".aml");
			filesAMLInRDF.convertRdf();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(expected = NullPointerException.class)
	public void amlFileShouldConvertToRdf() {
		filesAMLInRDF.convertRdf();
		fail("it must fail because no aml path is given");

	}

	@Test
	public void TurtleFormatShouldRead() {
		try {
			filesAMLInRDF.readFiles(ConfigManager.getFilePath(), ".ttl");
			filesAMLInRDF.generateExtensionalDB(ConfigManager.getFilePath());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
