package main;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import javax.security.auth.Subject;

import util.ConfigManager;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

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
	 */
	public void readFiles(String path){
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
		
		factsFromFiles();
	}
	
	public void factsFromFiles(){
		
		if(files.size()!=0){
			for (File file : files) {
				System.out.println(file.getAbsolutePath() + " AA");
				InputStream inputStream = FileManager.get().open(file.getAbsolutePath());
				model.read(new InputStreamReader(inputStream), null, "TURTLE"); // parses an InputStream assuming RDF in Turtle format

				//literals = new ArrayList<RDFNode>();
				//predicates = new ArrayList<RDFNode>();

				StmtIterator iterator = model.listStatements();

				while (iterator.hasNext()) {
					Statement stmt = iterator.nextStatement();
					
					predicate = stmt.getPredicate();
					//predicates.add(predicate);

					literal = stmt.getObject();
					//literals.add(literal);
					subject = stmt.getSubject();
					
					System.out.println(subject + "" + predicate + "" + "" + literal);

				}
				
				
			}
			
		}else{
			System.out.println(" NO files");
		}
	}
		
		
	
}
