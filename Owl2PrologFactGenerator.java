import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
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
import org.cs3.prolog.connector.Connector;
import org.cs3.prolog.connector.common.QueryUtils;
import org.cs3.prolog.connector.process.PrologProcess;
import org.cs3.prolog.connector.process.PrologProcessException;
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

/**
 * 
 * Generates the Datalog facts from a given Ontology
 */
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
			buf.append(":-dynamic(" + StringUtil.lowerCaseFirstChar(objProp.getIRI().getFragment()) +"/2).").
			append(System.getProperty("line.separator"));
		}

		for (OWLProperty dataProp : ont.getDataPropertiesInSignature()) {
			buf.append(":-dynamic(" + StringUtil.lowerCaseFirstChar(dataProp.getIRI().getFragment()) +"/2).").
			append(System.getProperty("line.separator"));
		}
		
		return buf.toString();
	}

	/**
	 * Generate facts from the TBOX. Classes, objects and datatype properties
	 * @return
	 */
	public String factsFromTBox(){
		StringBuilder buf = new StringBuilder();
		
		for (OWLClass cls : ont.getClassesInSignature()) {
			buf.append("clause1(type(").
			append(StringUtil.lowerCaseFirstChar(cls.getIRI().getFragment())).
			append(",").
			append("class),true).");
			buf.append(System.getProperty("line.separator"));
		}

		for (OWLProperty objProp : ont.getObjectPropertiesInSignature()) {
			buf.append("clause1(type(").
			append(StringUtil.lowerCaseFirstChar(objProp.getIRI().getFragment())).
			append(",").
			append("objectproperty),true).");
			buf.append(System.getProperty("line.separator"));
		}

		for (OWLProperty datatypeProp : ont.getDataPropertiesInSignature()) {
			buf.append("clause1(type(").
			append(StringUtil.lowerCaseFirstChar(datatypeProp.getIRI().getFragment())).
			append(",").
			append("datatypeproperty),true).");
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
			append(StringUtil.lowerCaseFirstChar(ind.getIRI().getFragment())).
			append(",").
			append(StringUtil.lowerCaseFirstChar(cls.getIRI().getFragment())).
			append("),true).");
			buf.append(System.getProperty("line.separator"));

			// look up all object property assertions
			for (OWLObjectProperty op: ont.getObjectPropertiesInSignature()) {
				NodeSet<OWLNamedIndividual> objPropSet = reasoner.getObjectPropertyValues(ind, op);
				for (OWLNamedIndividual value : objPropSet.getFlattened()){
					//System.out.println(ind.getIRI().getFragment() + " " + op.getIRI().getFragment() + " " + value.getIRI().getFragment());
					buf.append("clause1("+ op.getIRI().getFragment() +"(").
					append(StringUtil.lowerCaseFirstChar(ind.getIRI().getFragment())).
					append(",").
					append(StringUtil.lowerCaseFirstChar(value.getIRI().getFragment())).
					append("),true).");
					buf.append(System.getProperty("line.separator"));
				}
			}
			
			// look up all datatype property assertions
			for (OWLDataProperty dp: ont.getDataPropertiesInSignature()) {
				Set<OWLLiteral> dataPropSet = reasoner.getDataPropertyValues(ind, dp);
				for (OWLLiteral value : dataPropSet){
					buf.append("clause1("+ dp.getIRI().getFragment() +"(").
					append(ind.getIRI().getFragment().toLowerCase()).
					append(",");
					if(value.hasLang()){
						//@todo Removing the @en from the last part of the string. This should be a different way to do it
						String removeAdd = value.toString().substring(0, value.toString().length() - 3);
						buf.append(removeAdd);
					}else{
						buf.append(value);
					}
					buf.append("),true).").
					append(System.getProperty("line.separator"));
				}
			}
		}

		return buf.toString();
	}
	
	/**
	 * Get all the domain and range axioms of the ontology
	 */
	public void getDomainRangeAxioms(){
		Set<OWLDataPropertyDomainAxiom> dataPropDomain = ont.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN);
		for (OWLDataPropertyDomainAxiom owlDataPropertyDomainAxiom : dataPropDomain) {
			System.out.println(((OWLNamedObject) owlDataPropertyDomainAxiom.getDomain()).getIRI().getFragment() + " " + 
					         ((OWLNamedObject) owlDataPropertyDomainAxiom.getProperty()).getIRI().getFragment());
		}
		
		Set<OWLDataPropertyRangeAxiom> dataPropRange = ont.getAxioms(AxiomType.DATA_PROPERTY_RANGE);
		for (OWLDataPropertyRangeAxiom dataPropRangeAxiom : dataPropRange) {
			System.out.println(((OWLNamedObject) dataPropRangeAxiom.getRange()) + " " + 
					         ((OWLNamedObject) dataPropRangeAxiom.getProperty()).getIRI().getFragment());
		}
		
		Set<OWLObjectPropertyDomainAxiom> objPropDomain = ont.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN);
		for (OWLObjectPropertyDomainAxiom owlObjPropDomainAxiom : objPropDomain) {
			System.out.println(((OWLNamedObject) owlObjPropDomainAxiom.getDomain()).getIRI().getFragment() + " " + 
		     ((OWLNamedObject) owlObjPropDomainAxiom.getProperty()).getIRI().getFragment());
		}
		
		Set<OWLObjectPropertyRangeAxiom> objPropRange = ont.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE);
		for (OWLObjectPropertyRangeAxiom ObjPropRangeAxiom : objPropRange) {
			System.out.println(((OWLNamedObject) ObjPropRangeAxiom.getRange()).getIRI().getFragment() + " " + 
		     ((OWLNamedObject) ObjPropRangeAxiom.getProperty()).getIRI().getFragment());
		}
	}
	
	public void consultKB() throws IOException, PrologProcessException{
		 PrologProcess process = Connector.newPrologProcess();
         //String consultQuery = QueryUtils.bT("reconsult", "'d:/Deutch/development/Rules4AMLIntegration/resources/father.pl'");
         String consultQuery = QueryUtils.bT("reconsult", "'d:/Deutch/development/Rules4AMLIntegration/resources/ExtensionalDB.pl'");
         process.queryOnce(consultQuery);
         // create query with the buildTerm method
         // this is the same as "father_of(Father, peter)"
         String query = QueryUtils.bT("father_of", "Father", "peter");
         //String query = "clause1(type(A,roleClass),true).";
         // get the first result of the query (ignore other results if there are any)
         //Map<String, Object> result = process.queryOnce(query);
         /*if (result == null) {
             System.out.println("peter has no father");
         } else {
             System.out.println(result.get("Father") + " is the father of peter");
         }*/

         // get ALL results of the query as a list
         // every element in this list is one result
         // if the query fails, the list will be empty (but it won't be null)
         List<Map<String, Object>> results = process.queryAll(query);
         for (Map<String, Object> r : results) {
             // iterate over every result
        	 
             System.out.println(r.get("Child") + " is a child of john");
         }
	}
	
	

	public OWLOntology getOnt() {
		return ont;
	}

	public void setOnt(OWLOntology ont) {
		this.ont = ont;
	}	

}
