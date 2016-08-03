package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

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
	private static ArrayList<RDFNode> literals, predicates;
	private static Model model;
	private ArrayList<File> files;

	/**
	 * Read the rdf files of a given path
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void readFiles(String path) throws Exception {
		files = new ArrayList<File>();
		model = ModelFactory.createDefaultModel();
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
		model.read(new InputStreamReader(inputStream), null, "TURTLE"); // parses
																		// an
																		// InputStream
																		// assuming
																		// RDF
																		// in
																		// Turtle
																		// format

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
	public void generatePrologFile(File rdfAMLFilePath, int number) throws Exception {
		String newName = FilenameUtils.getBaseName(rdfAMLFilePath.getName()) + ".pl";
		String completePath = rdfAMLFilePath.getParentFile() + "\\" + newName;
		PrintWriter prologWriter = new PrintWriter(new FileWriter(completePath), true);
		prologWriter.println(factsFromFiles(rdfAMLFilePath, number));
		prologWriter.flush();
		prologWriter.close();
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
	public void generateFiles() throws Exception {
		int i = 2;
		for (File file : files) {
			generatePrologFile(file, i++);
		}

	}

	public void callMergeFiles() {
		String sourceFile1Path = "C:/HeterogeneityExampleData/AutomationML/M2-Granularity/Testbeds-2/plfile0.pl";
		String sourceFile2Path = "C:/HeterogeneityExampleData/AutomationML/M2-Granularity/Testbeds-2/plfile1.pl";

		String mergedFilePath = "C:/HeterogeneityExampleData/AutomationML/M2-Granularity/Testbeds-2/edb.pl";

		File[] files = new File[2];
		files[0] = new File(sourceFile1Path);
		files[1] = new File(sourceFile2Path);

		File mergedFile = new File(mergedFilePath);
		mergeFiles(files, mergedFile);
	}

	public void mergeFiles(File[] files, File mergedFile) {

		FileWriter fstream = null;
		BufferedWriter out = null;
		try {
			fstream = new FileWriter(mergedFile, true);
			out = new BufferedWriter(fstream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (File f : files) {
			System.out.println("merging: " + f.getName());
			FileInputStream fis;
			try {
				fis = new FileInputStream(f);
				BufferedReader in = new BufferedReader(new InputStreamReader(fis));

				String aLine;
				while ((aLine = in.readLine()) != null) {
					out.write(aLine);
					out.newLine();
				}

				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}