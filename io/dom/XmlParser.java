package io.dom;

import cn.qingweico.io.Print;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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

    public static void sax(String xmlFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    Print.grace("Start Element", qName);
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    Print.grace("End Element", qName);
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    Print.grace("Characters", new String(ch, start, length));
                }
            };

            InputStream inputStream = new FileInputStream(xmlFile);
            saxParser.parse(inputStream, handler);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        sax("logback.xml");
    }

}
