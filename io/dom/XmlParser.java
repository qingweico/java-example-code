package io.dom;

import cn.qingweico.io.Print;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * @author zqw
 * @date 2025/8/9
 */
@Slf4j
public class XmlParser {
    public static void printXml(String file) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new File(file));
            Element root = doc.getDocumentElement();
            Print.println(root.getNodeName());
            NodeList nodes = root.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Print.println("\t" + node.getNodeName());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
