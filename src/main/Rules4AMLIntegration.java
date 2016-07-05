package main;

import java.io.IOException;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import util.ConfigManager;


public class Rules4AMLIntegration {

	public static void main(String[] args) {
		Owl2PrologFactGenerator test = new Owl2PrologFactGenerator();
		//DeductiveDB deductiveDB = new DeductiveDB();
		//deductiveDB.consultKB();
		
		ConfigManager conf = new ConfigManager();
		conf.loadConfig();
		
		try {
			// Generating rules from the Ontology
			String localIRI = conf.getOntoURIPath();
			test.readOntology(localIRI);
			test.generateTBoxFacts(conf.getFilePath() + "IntentionalDB.pl");
			
			// Generating facts from the Ontology
			test.generateABoxFacts(conf.getFilePath() + "ExtensionalDB.pl");
			
			// Generating facts from the AML files, they are converted into RDF 
			Files2Facts filesAMLInRDF = new Files2Facts();
			filesAMLInRDF.readFiles(conf.getFilePath());
			filesAMLInRDF.generateFiles();
			
		} catch (OWLOntologyCreationException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
