package integration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import main.DeductiveDB;
import util.ConfigManager;

/**
 * @author omar
 *
 */
public class XmlParser {

	private static XPathFactory xpf;
	private static XPath xpath;
	public ArrayList<Node> integrationNodes;
	public ArrayList<Node> seedNodes;

	/*
	 * Constructor initializes
	 */
	public XmlParser() {
		integrationNodes = new ArrayList<Node>();
		seedNodes = new ArrayList<Node>();
		xpf = XPathFactory.newInstance();
		xpath = xpf.newXPath();

	}

	/**
	 * @return the integrationNodes
	 */
	public ArrayList<Node> getIntegrationNodes() {
		return integrationNodes;
	}

	/**
	 * @return the seedNodes
	 */
	public ArrayList<Node> getSeedNodes() {
		return seedNodes;
	}

	/**
	 * initialized the document on which the integration is to be performed.
	 * 
	 * @param inputFile
	 * @return
	 */
	public Document initInput(String inputFile) {

		Document seed = null;
		try {
			seed = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(inputFile));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			System.out.println("Error File not Found " + inputFile);
			System.out.println("Please check configuration file");
			e.printStackTrace();
		}
		return seed;

	}

	/**
	 * Gets all the nodes from the two seeds.
	 * 
	 * @param seed
	 * @param integration
	 */
	protected void getAllNodes(Document seed, Document integration) {

		// Queries both documents to get All attribute values.
		try {
			NodeList nodes = (NodeList) xpath.evaluate("//*/@*", seed, XPathConstants.NODESET);

			NodeList nodes2 = (NodeList) xpath.evaluate("//*/@*", integration, XPathConstants.NODESET);

			// stores all the attributes results in arrayList
			setNodes(nodes, 1);
			setNodes(nodes2, 2);

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

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
	 * looping through the seedNode which will be compared to matching elements
	 * in output.txt
	 * 
	 * @param i
	 * @return
	 * @throws DOMException
	 * @throws XPathExpressionException
	 * @throws IOException
	 */
	int compareConflicts(int i, Document seed, Document integration)
			throws XPathExpressionException, DOMException, IOException {
		// flag to check if the attribute value is inside matching
		int flag = 0;

		// loops through all its element.

		if (DeductiveDB.attrName != null) {
			for (int k = 0; k < DeductiveDB.attrName.size(); k++) {
				// we find its parent node so we can append it under it.

				if (seedNodes.get(i).getNodeName().equals(DeductiveDB.attrName.get(k).replaceAll("'", ""))) {
					// flag = 1;

				} else if (seedNodes.get(i).getTextContent().equals(DeductiveDB.attrName.get(k).replaceAll("'", ""))) {
					// if match is found
					flag = 1;

				}

				else {

					NodeList list = getAttributeNode(seedNodes.get(i).getTextContent(), seed);
					if (list.getLength() == 0) {
						list = getAttributeNode(seedNodes.get(i).getTextContent(), integration);

					}

					NodeList list2 = getAttributeNode(DeductiveDB.attrName.get(k).replaceAll("'", ""), seed);
					NodeList list3 = getAttributeNode(DeductiveDB.attrName.get(k).replaceAll("'", ""), integration);

					if (checkParent2(seedNodes.get(i), list2.item(0))) {
						flag = 1;

					}
					if (checkParent(seedNodes.get(i), list3.item(0))) {
						flag = 1;

					}

					for (int m = 0; m < list.getLength(); m++) {
						// matches the parent in the integration document.

						if (list.item(0).getParentNode() != null && list2.item(0) != null && list3.item(0) != null
								&& list.item(0) != null) {

							if (seedNodes.get(i).getTextContent().equals("Connection")) {

							}

							if (list.item(0).isEqualNode(list2.item(0)) || list.item(0).isEqualNode(list3.item(0))) {

								flag = 1;

							}

							if (list.item(0).getParentNode().isEqualNode(list2.item(0).getParentNode())
									|| list.item(0).getParentNode().isEqualNode(list3.item(0).getParentNode())) {

								// flag = 1;

								@SuppressWarnings("resource")
								BufferedReader br = new BufferedReader(
										new FileReader(ConfigManager.getFilePath() + "/output.txt"));
								String line = br.readLine();

								while (line != null) {
									if (line.contains("concatString")) {
										flag = 1;
										break;

									}
									line = br.readLine();
								}
							}

						}
					}
				}

				// ignore FileName attribute
				if (seedNodes.get(i).getNodeName().equals("FileName")) {

					// not in output.txt
					flag = 1;
				}

			}

		}

		return flag;
	}

	/**
	 * We are interested if its not in output.txt . We need to add non matching
	 * elements to the integration file. All matching elements were already
	 * included when we copied one of the seed files.
	 * 
	 * @param i
	 * @param seed
	 * @param integration
	 * @return
	 */
	int compareNonConflicts(int i, Document seed, Document integration) {

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

		return check;

	}

	/**
	 * We are interested if its not in output.txt . We need to add non matching
	 * elements to the integration file. All matching elements were already
	 * included when we copied one of the seed files. This comparison is for
	 * node Values.
	 * 
	 * @param i
	 * @param seed
	 * @param integration
	 * @return
	 */

	int compareNonConflictsValues(int i, Document seed, Document integration) {

		int check = 0;

		// for every node in Integration File compare the seed nodes.
		for (int j = 0; j < integrationNodes.size(); j++) {

			// compares the attribute Node with Node name
			if (seedNodes.get(i).getNodeName().equals(integrationNodes.get(j).getNodeName())) {

				// compares its Node and Parent node for semantic
				// equality.
				if (checkParent(seedNodes.get(i), integrationNodes.get(j))) {

					// if all test passes we can say its already in
					// the integration document. we can ignore it.
					check = 1;
				}
			}
		}

		return check;

	}

	/**
	 * For the current node that is identified as non conflicting element and
	 * which is not found in integration.aml , we must add it . We add it
	 * throught migration of the node.
	 * 
	 * @param i
	 * @param seed
	 * @param integration
	 * @throws XPathExpressionException
	 * @throws DOMException
	 */
	void addNonConflicts(int i, Document seed, Document integration) throws XPathExpressionException, DOMException {
		// we get the Node of the attribute Value which is not
		// found. i represent that node.

		NodeList list = getAttributeNode(seedNodes.get(i).getTextContent(), seed);

		// we find its parent node so we can append it under it.
		for (int m = 0; m < list.getLength(); m++) {

			// matches the parent in the integration document.

			if (!list.item(m).getParentNode().getNodeName().equals("#document")) {
				NodeList integ = (NodeList) xpath.evaluate("//" + list.item(m).getParentNode().getNodeName(),
						integration, XPathConstants.NODESET);
				// now we have the parent name and the nodes to be
				// added.we export it to integration.aml file.
				int index = 0;
				for (int z = 0; z < integ.getLength(); z++) {
					// to transfer node from one document to another it
					// must adopt that node.

					if (checkParent(list.item(m).getParentNode(), integ.item(z))) {

						index = z;
					}
				}
				integ.item(index).getOwnerDocument().adoptNode(list.item(m));

				// now we can add under the parent.
				integ.item(index).appendChild(list.item(m));

			}
		}

	}

	/**
	 * For the current node that is identified as non conflicting element and
	 * which is not found in integration.aml , we must add it . We add it
	 * throught migration of the node.
	 * 
	 * @param i
	 * @param seed
	 * @param integration
	 * @throws XPathExpressionException
	 * @throws DOMException
	 */
	void addNonConflictsValues(int i, Document seed, Document integration)
			throws XPathExpressionException, DOMException {

		// we get the Node which is not
		// found. i represent that node.

		Node node = getSeedNodes().get(i);
		// we find its parent node so we can append it under it.

		// matches the parent in the integration document.
		NodeList integ = (NodeList) xpath.evaluate("//" + node.getParentNode().getNodeName(), integration,
				XPathConstants.NODESET);
		// now we have the parent name and the nodes to be
		// added.we export it to integration.aml file.

		// to transfer node from one document to another it
		// must adopt that node.
		for (int z = 0; z < integ.getLength(); z++) {

			integ.item(z).getOwnerDocument().adoptNode(node);

			// now we can add under the parent.
			integ.item(z).appendChild(node);
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

		Object result;

		result = (Object) xpath.evaluate("//*[@*=\"" + value + "\"]", seed, XPathConstants.NODESET);
		NodeList nodeList = (NodeList) result;

		if (nodeList.getLength() != 0) {

		}
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

	static boolean checkParent2(Node seed, Node integration) {
		// loops until there are no more parents.

		while (seed.getParentNode() != null) {

			// compares parents name
			if (seed != null && integration != null) {

				if (!seed.getNodeName().equals(integration.getNodeName())) {

					// if equal puts next parent
					seed = seed.getParentNode();

				}

				else {

					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	void finalizeIntegration(Document integration) throws TransformerFactoryConfigurationError, Throwable {
		// finally we update our integration.aml file.
		Transformer xformer = TransformerFactory.newInstance().newTransformer();
		xformer.transform(new DOMSource(integration),
				new StreamResult(new File(ConfigManager.getFilePath() + "integration/integration.aml")));

	}

	/***
	 * This updates seed and integration array with Elemen Values, <WriterName>
	 * Value </> This value is required for integration of such nodes.
	 * 
	 * @param seed
	 * @param integration
	 * @throws XPathExpressionException
	 */

	void setNodeValues(Document seed, Document integration) throws XPathExpressionException {
		seedNodes = new ArrayList<Node>();

		NodeList integrationElements = (NodeList) xpath.evaluate("//text()", seed, XPathConstants.NODESET);
		for (int z = 0; z < integrationElements.getLength(); z++) {

			if (!integrationElements.item(z).getNodeValue().toString().trim().equals("")) {
				seedNodes.add(integrationElements.item(z).getParentNode());
			}
			integrationNodes = new ArrayList<Node>();
			NodeList nodes2 = (NodeList) xpath.evaluate("//*", integration, XPathConstants.NODESET);
			setNodes(nodes2, 2);
		}
	}

}
