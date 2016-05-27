import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Iterator;
import java.util.Set;




import org.apache.commons.lang3.StringUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.HermiT.Reasoner.ReasonerFactory;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Node_URI;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

public class Owl2PrologFactGenerator {
	
	private OWLOntology ont;
	private OWLReasoner reasoner;
	private String inputOwlFilename;

	/**
	 * Read the ontology given a local path
	 * @throws OWLOntologyCreationException
	 */
	public void readOntology(String localIRI) throws OWLOntologyCreationException{
		String prefix = "file:";
		this.inputOwlFilename = localIRI;
		// windows path fixing
		if (!localIRI.startsWith("/"))
			prefix += "/";
		URI basePhysicalURI = URI.create(prefix + localIRI.replace("\\", "/"));
		OWLOntologyManager baseM = OWLManager.createOWLOntologyManager();
		this.ont = baseM.loadOntologyFromOntologyDocument(IRI.create(basePhysicalURI));
		reasoner = new Reasoner.ReasonerFactory().createReasoner(this.ont);
	}

	public void generateTBoxFacts(String outputPrologFilename) throws Exception {
		PrintWriter prologWriter = 
				new PrintWriter(new FileWriter(outputPrologFilename), true);
		prologWriter.println(factsFromProperties());
		prologWriter.println(factsFromTBox());
		prologWriter.flush();
		prologWriter.close();
	}
	
	public void generateABoxFacts(String AboxFilePath) throws Exception {
		PrintWriter prologWriter = 
				new PrintWriter(new FileWriter(AboxFilePath), true);
		for (OWLClass cls : ont.getClassesInSignature()) {
			prologWriter.println(factsFromABox(cls));
	    }
		prologWriter.flush();
		prologWriter.close();
	}
	
	
	/**
	 * Write properties on top of the file
	 * @return
	 */
	public String factsFromProperties(){
		StringBuilder buf = new StringBuilder();
		
		for (OWLProperty objProp : ont.getObjectPropertiesInSignature()) {
			buf.append(":-dynamic(" + objProp.getIRI().getFragment() +"/2).").
			append(System.getProperty("line.separator"));
	    }
		
		for (OWLProperty objProp : ont.getDataPropertiesInSignature()) {
			buf.append(":-dynamic(" + objProp.getIRI().getFragment() +"/2).").
			append(System.getProperty("line.separator"));
	    }
		
		return buf.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public String factsFromTBox(){
		StringBuilder buf = new StringBuilder();
		
		for (OWLProperty objProp : ont.getObjectPropertiesInSignature()) {
			buf.append("clause1(type(").
			append(objProp.getIRI().getFragment()).
			append(",").
			append("ObjectProperty),true).");
			buf.append(System.getProperty("line.separator"));
	    }
		
		for (OWLClass cls : ont.getClassesInSignature()) {
			buf.append("clause1(type(").
			append(cls.getIRI().getFragment()).
			append(",").
			append("Class),true).");
			buf.append(System.getProperty("line.separator"));
	    }
		
		for (OWLProperty datatypeProp : ont.getDataPropertiesInSignature()) {
			buf.append("clause1(type(").
			append(datatypeProp.getIRI().getFragment()).
			append(",").
			append("DatatypeProperty),true).");
			buf.append(System.getProperty("line.separator"));
	    }
		
		return buf.toString();
	}
	
	/**
	 * Gets the Individuals of all the classes in the ontology and generates the corresponding facts 
	 * @return A buffer of Strings
	 */
	public String factsFromABox(OWLClass cls){
		StringBuilder buf = new StringBuilder();
	        NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(cls, false);
	        for (OWLNamedIndividual ind : instances.getFlattened()) {
	            buf.append("clause1(type(").
				append(ind.getIRI().getFragment()).
				append(",").
				append(cls.getIRI().getFragment()).
				append("),true).");
				buf.append(System.getProperty("line.separator"));
		}
		return buf.toString();
	}
	
	public OWLOntology getOnt() {
		return ont;
	}

	public void setOnt(OWLOntology ont) {
		this.ont = ont;
	}	

}
