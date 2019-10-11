package com.kaywall.design.factory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年10月11日 14:57
 */
public class ReadXmlUtils {

	/**
	 * XML配置文件中提取具体类类名
	 * @return
	 */
	public static Object getObject(){
		try {
			//创建文档对象
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dFactory.newDocumentBuilder();
			Document doc = builder.parse(new File("pattern/design-patterns/src/main/resources/config.xml"));
			//获取包含类名的文本节点
			NodeList nl = doc.getElementsByTagName("className");
			Node classNode = nl.item(0).getFirstChild();
			String cName = "com.kaywall.design.factory." + classNode.getNodeValue();
			System.out.println("新类名：" + cName);
			//通过类名生成实例对象并将其返回
			Class<?> c = Class.forName(cName);
			Object obj = c.newInstance();
			return obj;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
