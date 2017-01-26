package main;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import edu.unibonn.i4matcher.helpers.OPCUA.XML2OWLMapper;
import edu.unibonn.i4matcher.helpers.OPCUA.XSD2OWLMapper;

public class RDFTransformer {

	public void transform(String input, String output) {

		ClassLoader classLoader = getClass().getClassLoader();

		XSD2OWLMapper mapping = new XSD2OWLMapper(classLoader.getResourceAsStream("opcua.xsd"));
		mapping.setObjectPropPrefix("");
		mapping.setDataTypePropPrefix("has");
		mapping.convertXSD2OWL();

		XML2OWLMapper generator = new XML2OWLMapper(new File(input), mapping);
		generator.convertXML2OWL();

		// This part prints the opcua RDF data model to the specified file.
		try {
			File f = new File(output);
			f.getParentFile().mkdirs();
			Writer writer = new FileWriter(f);
			generator.writeModel(writer, "TTL");
			writer.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
