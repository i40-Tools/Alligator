package main;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

import javax.security.auth.Subject;

import org.semanticweb.owlapi.model.OWLClass;

import util.ConfigManager;
import util.StringUtil;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

import org.apache.commons.io.FilenameUtils;

/**
 * Reads the RDF files and convert them to Datalog facts
 * @author Irlan
 * 28.06.2016
 */
public class Files2Facts {
	private RDFNode object;
	private RDFNode predicate;
	private RDFNode subject;
	private static ArrayList<RDFNode> literals, predicates;
	private static Model model;
	private ArrayList<File> files;

	/**
	 * Read the rdf files of a given path
	 * @param path
	 * @throws Exception 
	 */
	public void readFiles(String path) throws Exception{
		files = new ArrayList<File>();
		model = ModelFactory.createDefaultModel();
		File originalFilesFolder = new File(path);
		if (originalFilesFolder.isDirectory()) {
			for (File amlFile : originalFilesFolder.listFiles()) {
				if (amlFile.isFile() && 
						(amlFile.getName().endsWith(".ttl"))) {
					files.add(amlFile);
				}
			}
		}else{
			System.out.println("Error in the directory that you provided");
			System.exit(0);
		}
	}

	public String factsFromFiles(File file, String test) throws Exception{
		StringBuilder buf = new StringBuilder();
		InputStream inputStream = FileManager.get().open(file.getAbsolutePath());
		model.read(new InputStreamReader(inputStream), null, "TURTLE"); // parses an InputStream assuming RDF in Turtle format

		StmtIterator iterator = model.listStatements();

		while (iterator.hasNext()) {
			Statement stmt = iterator.nextStatement();
			subject = stmt.getSubject();
			predicate = stmt.getPredicate();
			object = stmt.getObject();
			
			String subjectStr = StringUtil.removeLastMinus(subject.asNode().getLocalName());
			
			buf.append("clause1(").
			append(predicate.asNode().getLocalName()).
			append("(").
			append(StringUtil.lowerCaseFirstChar(subjectStr + test)).
			append(",");
			
			if (object.isURIResource()) {
				object = model.getResource(object.as(Resource.class).getURI());
				String objectStr = object.asNode().getLocalName();
				buf.append(StringUtil.lowerCaseFirstChar(StringUtil.removeLastMinus(objectStr) + test));
			}else{
				if(object.isLiteral()){
					buf.append("'" + object.asLiteral().getLexicalForm() + "'");
				}else{
					buf.append(object);
				}
			}
			
			buf.append("),true).");
			buf.append(System.getProperty("line.separator"));
		}
		return buf.toString();
	}

	/**
	 * @param rdfAMLFilePath
	 * @throws Exception
	 */
	public void generatePrologFile(File rdfAMLFilePath) throws Exception {
		String newName = FilenameUtils.getBaseName(rdfAMLFilePath.getName()) + ".pl";
		String completePath = rdfAMLFilePath.getParentFile() + "\\" + newName;
		PrintWriter prologWriter = 
				new PrintWriter(new FileWriter(completePath), true);
		prologWriter.println(factsFromFiles(rdfAMLFilePath,""));
		prologWriter.flush();
		prologWriter.close();
	}
	
	/**
	 * @param rdfAMLFilePath
	 * @throws Exception
	 */
	public void generateExtensionalDB(String path) throws Exception {
		StringBuilder buf = new StringBuilder();
		int i = 1;
		for (File file : files) {
			buf.append(factsFromFiles(file,"_"+i++));
		}
		PrintWriter prologWriter = 
				new PrintWriter(new FileWriter(path + "edb.pl"), true);
		prologWriter.println(buf);
		prologWriter.flush();
		prologWriter.close();
	}

	
	/**
	 * Generate all the files of a given folder
	 * @throws Exception
	 */
	public void generateFiles() throws Exception{
		for (File file : files) {
			generatePrologFile(file);
		}
	}



}
