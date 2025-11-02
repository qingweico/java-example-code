package io.dom;

import object.entity.EntityToXml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * Java Architecture for XML Binding(JAXB) 用于Java类和XML之间的相互转换
 *
 * @author zqw
 * @date 2023/12/12
 * @see JAXBContext
 * @see Marshaller
 */
class Jaxb {
    public static void main(String[] args) throws JAXBException {
        EntityToXml etx = new EntityToXml("var1", "var2");
        JAXBContext context = JAXBContext.newInstance(EntityToXml.class);
        Marshaller marshaller = context.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(etx, writer);
        String xmlString = writer.toString();
        System.out.println(xmlString);
    }
}
