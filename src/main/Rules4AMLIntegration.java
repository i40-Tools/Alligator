package main;

import java.io.IOException;

import org.cs3.prolog.connector.process.PrologProcessException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;


public class Rules4AMLIntegration {

	public static void main(String[] args) {
		Owl2PrologFactGenerator test = new Owl2PrologFactGenerator();
		DeductiveDB deductiveDB = new DeductiveDB();
		deductiveDB.consultKB();
		/*try {
			String localIRI = "d:/Deutch/development/Rules4AMLIntegration/resources/aml.ttl";
			test.readOntology(localIRI);
			test.generateTBoxFacts("d:/Deutch/development/Rules4AMLIntegration/resources/IntentionalDB.pl");
			test.generateABoxFacts("d:/Deutch/development/Rules4AMLIntegration/resources/ExtensionalDB.pl");
			
			test.getDomainRangeAxioms();*/
			
		/*} catch (OWLOntologyCreationException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}

}
