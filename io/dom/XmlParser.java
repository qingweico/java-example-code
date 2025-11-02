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

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * SAX 是 Simple API for XML 的简称, 其作为 XML 处理的基石技术
 * w3c 以及 dom4j 和 jaxb 读取 xml
 *
 * @author zqw
 * @date 2025/8/9
 * @see cn.hutool.core.util.XmlUtil
 * @see cn.qingweico.convert.XmlConvert
 * @see DocumentBuilderFactory W3C DOM(Java 标准 API)
 * @see SAXParser 是 Java 中基于 SAX 的 XML 解析器(Java 标准 API)
 * @see org.dom4j.io.SAXReader DOM4J 库
 * @see JAXBContext Java 标准库中的 SAX 应用(JAXB)
 */
@Slf4j
public class XmlParser {

    /*W3C DOM 标准读XML*/
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
