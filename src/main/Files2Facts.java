
package main;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

import util.StringUtil;

/**
 * Reads the RDF files and convert them to Datalog facts
 * 
 * @author Irlan 28.06.2016
 */
public class Files2Facts {
	private RDFNode object;
	private RDFNode predicate;
	private RDFNode subject;
	private ArrayList<File> files;

	/**
	 * Read the rdf files of a given path
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void readFiles(String path) throws Exception {
		files = new ArrayList<File>();
		File originalFilesFolder = new File(path);
		if (originalFilesFolder.isDirectory()) {
			for (File amlFile : originalFilesFolder.listFiles()) {
				if (amlFile.isFile() && (amlFile.getName().endsWith(".ttl"))) {
					files.add(amlFile);
				}
			}
		} else {
			System.out.println("Error in the directory that you provided");
			System.exit(0);
		}
	}

	public String factsFromFiles(File file, int number) throws Exception {

		StringBuilder buf = new StringBuilder();

		InputStream inputStream = FileManager.get().open(file.getAbsolutePath());

		Model model = null;
		model = ModelFactory.createDefaultModel();

		// parses in turtle format
		model.read(new InputStreamReader(inputStream), null, "TURTLE");

		StmtIterator iterator = model.listStatements();

		while (iterator.hasNext()) {
			Statement stmt = iterator.nextStatement();
			subject = stmt.getSubject();
			predicate = stmt.getPredicate();
			object = stmt.getObject();

			buf.append("clause1(").append(predicate.asNode().getLocalName()).append("(")
					.append(StringUtil.lowerCaseFirstChar(subject.asNode().getLocalName()) + number).append(",");

			if (object.isURIResource()) {
				object = model.getResource(object.as(Resource.class).getURI());
				String objectStr = object.asNode().getLocalName();
				if (predicate.asNode().getLocalName().toString().equals("type")) {
					buf.append(StringUtil.lowerCaseFirstChar(objectStr));
				} else {
					buf.append(StringUtil.lowerCaseFirstChar(objectStr) + number);
				}

			} else {
				if (object.isLiteral()) {
					buf.append("'" + object.asLiteral().getLexicalForm() + "'");
				} else {
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
	public void generateExtensionalDB(String path) throws Exception {
		int i = 1;
		StringBuilder buf = new StringBuilder();
		;
		for (File file : files) {
			buf.append(factsFromFiles(file, i++));
		}
		PrintWriter prologWriter = new PrintWriter(new FileWriter(path + "edb.pl"), true);
		prologWriter.println(buf);
		prologWriter.flush();
		prologWriter.close();
	}

	/**
	 * Generate all the files of a given folder
	 * 
	 * @throws Exception
	 */

}