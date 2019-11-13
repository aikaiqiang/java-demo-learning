package com.kaywall.test.xml;

import org.apache.ibatis.io.Resources;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年09月10日 16:41
 */
public class XPathExample {

	public static void main(String[] args)
			throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		String resource = "mybatis/mybatis-config-dev.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		Document doc = builder.parse(inputStream);

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr = xpath.compile("/configuration/mappers/mapper");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			System.out.println(nodes.item(i).getAttributes().getNamedItem("resource").getNodeValue());
		}
	}
}
