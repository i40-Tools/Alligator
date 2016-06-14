package main;

import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

/**
 * 
 * @author Irlan
 *
 */
public class DeductiveDB {
	
	/**
	 * Querying the knowledge base. 
	 */
	public void consultKB() {
		String t7 = "consult('resources/semi1.pl')";
		System.out.println(t7 + " " + (Query.hasSolution(t7) ? "succeeded" : "failed"));
		
		String t8 = "consult('resources/exampleAML.pl')";
		System.out.println(t8 + " " + (Query.hasSolution(t8) ? "succeeded" : "failed"));
		
		String t9 = "tdb.";
		System.out.println(t9 + " " + (Query.hasSolution(t9) ? "succeeded" : "failed"));
		
		String stringFileQuery = "sameCAEXFile(X,Y)";
		System.out.println(stringFileQuery + " " + (Query.hasSolution(stringFileQuery) ? "succeeded" : "failed"));
		
		Query fileQuery = new Query(stringFileQuery);
		System.out.println("all solutions of " + stringFileQuery);
		while (fileQuery.hasMoreSolutions()) {
			Map<String, Term> s10 = fileQuery.nextSolution();
			System.out.println("X = " + s10.get("X") + ", Y = " + s10.get("Y"));
		}
	}

}
