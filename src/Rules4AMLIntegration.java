import org.semanticweb.owlapi.model.OWLOntologyCreationException;


public class Rules4AMLIntegration {

	public static void main(String[] args) {
		Owl2PrologFactGenerator test = new Owl2PrologFactGenerator();
		try {
			String localIRI = "d:/Deutch/development/Rules4AMLIntegration/resources/aml.ttl";
			test.readOntology(localIRI);
			test.generateTBoxFacts("d:/Deutch/development/Rules4AMLIntegration/resources/aml.pl");
			test.generateABoxFacts("d:/Deutch/development/Rules4AMLIntegration/resources/amlA.pl");
			
			
			
			
		} catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*test.setInputOwlFilename("d:/Deutch/development/Rules4AMLIntegration/resources/aml.ttl");
		test.setOutputPrologFilename("d:/Deutch/development/Rules4AMLIntegration/resources/aml.pl");
		try {
			test.generate();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
