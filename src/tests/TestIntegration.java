package tests;

import org.junit.BeforeClass;
import org.junit.Test;

import integration.Integration;
import integration.XmlParser;

public class TestIntegration {

	static Integration integ;
	static XmlParser xml;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		integ = new Integration();
		xml = new XmlParser();

	}

	@Test
	public void testIntegration() {

		try {
			integ.integrate();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
