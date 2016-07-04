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
	private RDFNode literal;
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

	public String factsFromFiles(File file) throws Exception{
		StringBuilder buf = new StringBuilder();
		InputStream inputStream = FileManager.get().open(file.getAbsolutePath());
		model.read(new InputStreamReader(inputStream), null, "TURTLE"); // parses an InputStream assuming RDF in Turtle format

		StmtIterator iterator = model.listStatements();

		while (iterator.hasNext()) {
			Statement stmt = iterator.nextStatement();
			subject = stmt.getSubject();
			predicate = stmt.getPredicate();
			literal = stmt.getObject();
			
			//System.out.println(subject);
			
			if (literal.isURIResource()) {
				literal = model.getResource(literal.as(Resource.class).getURI());
			}
			System.out.println(literal);

			buf.append("clause1(").
			append(subject).
			append("(").
			append(predicate).
			append(",").
			append(literal).
			append("),true).");
			buf.append(System.getProperty("line.separator"));
			//System.out.println(subject + "" + predicate + "" + "" + literal);
		}
		return buf.toString();
	}

	/**
	 * 
	 * @param AboxFilePath
	 * @throws Exception
	 */
	public void generateFiles(File rdfAMLFilePath) throws Exception {
		String newName = FilenameUtils.getBaseName(rdfAMLFilePath.getName()) + ".pl";
		String completePath = rdfAMLFilePath.getParentFile() + "\\" + newName;
		System.out.println(completePath);
		PrintWriter prologWriter = 
				new PrintWriter(new FileWriter(completePath), true);
		prologWriter.println(factsFromFiles(rdfAMLFilePath));
		prologWriter.flush();
		prologWriter.close();
	}

	public void test() throws Exception{
		for (File file : files) {
			generateFiles(file);
		}
	}



}
