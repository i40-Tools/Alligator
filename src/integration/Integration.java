package integration;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import main.DeductiveDB;
import util.ConfigManager;

public class Integration {

	public ArrayList<Node> integrationNodes;
	public ArrayList<Node> seedNodes;
	private static XPathFactory xpf;
	private static XPath xpath;

	/**
	 * This method integrates the two AML files.
	 * 
	 * @throws Throwable
	 */
	public void integrate() throws Throwable {
		// arrayList to hold nodes for integration and orignal files.
		integrationNodes = new ArrayList<>();
		seedNodes = new ArrayList<>();

		// reads one of AML file contents
		String contents = FileUtils.readFileToString(new File(ConfigManager.getFilePath() + "/seed-Granularity-1.aml"),
				"UTF-8");

		// One of the AML file will have its contents copied as it is.
		PrintWriter prologWriter = new PrintWriter(new File(ConfigManager.getFilePath() + "/integration.aml"));
		prologWriter.println(contents);
		prologWriter.close();

		// Get the output.txt contents which show matching elements.

		// using XPath for XML
		xpf = XPathFactory.newInstance();
		xpath = xpf.newXPath();

		// initializing documents.
		Document seed = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new InputSource(ConfigManager.getFilePath() + "/seed-Granularity-0.aml"));
		Document integration = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new InputSource(ConfigManager.getFilePath() + "/integration.aml"));

		// Queries both documents to get All attribute values.
		NodeList nodes = (NodeList) xpath.evaluate("//*/@*", seed, XPathConstants.NODESET);
		NodeList nodes2 = (NodeList) xpath.evaluate("//*/@*", integration, XPathConstants.NODESET);

		// stores all the attributes in arrayList
		setNodes(nodes, 1);
		setNodes(nodes2, 2);

		// looping through the seedNode which will be compared to matching
		// elements in output.txt
		for (int i = 0; i < seedNodes.size(); i++) {

			// flag to check if the attribute value is inside matching elements.
			int flag = 0;

			// loops through all its element.
			for (int k = 0; k < DeductiveDB.attrName.size(); k++) {
				if (seedNodes.get(i).getTextContent().equals(DeductiveDB.attrName.get(k))) {
					// if match is found
					flag = 1;
				}
				if (seedNodes.get(i).getNodeName().equals("FileName")) {
					// not in output.txt
					flag = 1;
				}

			}

			// we are interested if its not in output.txt
			// we need to add non matching elements to the integration file.
			// all matching elements were already included when we copied one of
			// the seed files.

			if (flag == 0) {

				// check tells if the non matching elements are already inside
				// xml or not
				int check = 0;

				// for every node in Integration File compare the seed nodes.
				for (int j = 0; j < integrationNodes.size(); j++) {

					// compares the attribute Node with its values
					if (seedNodes.get(i).getTextContent().equals(integrationNodes.get(j).getTextContent())) {

						// compares the attribute Node with Node name
						if (seedNodes.get(i).getNodeName().equals(integrationNodes.get(j).getNodeName())) {

							// compares its Node and Parent node for semantic
							// equality.
							if (checkAttributeNode(seedNodes.get(i).getTextContent(), seed,
									integrationNodes.get(j).getTextContent(), integration)) {

								// if all test passes we can say its already in
								// the integration document. we can ignore it.
								check = 1;
							}
						}
					}
				}

				// if the node is not in integration file we should add it.
				if (check != 1) {

					// we get the Node of the attribute Value which is not
					// found.
					NodeList list = getAttributeNode(seedNodes.get(i).getTextContent(), seed);

					// we find its parent node so we can append it under it.
					for (int m = 0; m < list.getLength(); m++) {

						// matches the parent in the integration document.
						NodeList integ = (NodeList) xpath.evaluate("//" + list.item(m).getParentNode().getNodeName(),
								integration, XPathConstants.NODESET);

						// now we have the parent name and the nodes to be
						// added.we export it to integration.aml file.

						for (int z = 0; z < integ.getLength(); z++) {

							// to transfer node from one document to another it
							// must adopt that node.
							integ.item(z).getOwnerDocument().adoptNode(list.item(m));

							// now we can add under the parent.
							integ.item(z).appendChild(list.item(m));

						}

					}

				}

			}
		}

		// finally we update our integration.aml file.
		Transformer xformer = TransformerFactory.newInstance().newTransformer();
		xformer.transform(new DOMSource(integration),
				new StreamResult(new File(ConfigManager.getFilePath() + "/integration.aml")));

	}

	/**
	 * Stores All the attributes values in arrayList of a given XML document
	 * 
	 * @param nodes
	 * @param file
	 */
	void setNodes(NodeList nodes, int file) {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (file == 1) {
				seedNodes.add(node);
			} else {
				integrationNodes.add(node);
			}

		}
	}

	/**
	 * This method takes Attribute Value and compares its Node Name. for e.g <
	 * Attribute speed="abc"> this method takes "abc " as input and compares
	 * node Attribute, if its equal or not.Because we must also check not only
	 * value but its node name as well.This method will also check the parent
	 * node of Attribute.They all must be equal in order to say that element is
	 * already in integration file.
	 * 
	 * @param value
	 * @param seed
	 * @param value2
	 * @param integration
	 * @return
	 */

	boolean checkAttributeNode(String value, Document seed, String value2, Document integration) {
		try {

			// for both files gets it Attribute Node.
			NodeList seedFile = getAttributeNode(value, seed);
			NodeList integrationFile = getAttributeNode(value2, integration);

			// compares with the integration file if its already included or
			// not.
			if (seedFile.getLength() > 0) {
				for (int i = 0; i < seedFile.getLength(); i++) {
					org.w3c.dom.Node node = seedFile.item(i);
					for (int j = 0; j < integrationFile.getLength(); j++) {
						org.w3c.dom.Node node2 = integrationFile.item(i);
						if (node.getNodeName().equals(node2.getNodeName())) {

							// for The node , it checks it parent
							if (checkParent(node, node2)) {
								return true;
							}

						}
					}
				}
			}

		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method takes Attribute Value for e.g < Attribute speed="abc"> this
	 * method takes "abc " as input and returns the Node of the attribute.In
	 * this case <Attribute>
	 * 
	 * @param value
	 * @param seed
	 * @return
	 * @throws XPathExpressionException
	 */
	static NodeList getAttributeNode(String value, Document seed) throws XPathExpressionException {
		// xpath query to get the Attribute

		Object result = (Object) xpath.evaluate("//*[@*=\"" + value + "\"]", seed, XPathConstants.NODESET);
		NodeList nodeList = (NodeList) result;
		return nodeList;

	}

	/**
	 * For every node that is checked , it should also be semantically correct.
	 * So its parents nodes are also checked and they should match to be
	 * qualified as relevant text.
	 * 
	 * @param seed
	 * @param integration
	 * @return
	 */

	static boolean checkParent(Node seed, Node integration) {

		// loops until there are no more parents.
		while (seed.getParentNode() != null && integration.getParentNode() != null) {

			// compares parents name
			if (seed.getParentNode().getNodeName().equals(integration.getParentNode().getNodeName())) {

				// if equal puts next parent
				seed = seed.getParentNode();

				integration = integration.getParentNode();

			} else {

				return false;
			}
		}

		return true;
	}

	public static void main(String[] args) throws Throwable {

		Integration integ = new Integration();
		integ.integrate();
		// for (int j = 0; j < DeductiveDB.attrName.size(); j++) {
		// Object result = (Object) xpath.evaluate("//*[@*=\"" +
		// DeductiveDB.attrName.get(j).replace("'", "") + "\"]",
		// seed, XPathConstants.NODESET);
		// NodeList nodeList = (NodeList) result;
		// if (nodeList.getLength() > 0) {
		// for (int i = 0; i < nodeList.getLength(); i++) {
		// org.w3c.dom.Node node = nodeList.item(i);
		// // System.out.println(node.getNodeName());
		// // System.out.println(DeductiveDB.attrName.get(j));
		//
		// }
		// }
		// }
	}

}
