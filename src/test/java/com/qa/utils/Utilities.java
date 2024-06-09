package com.qa.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.InputStream;
import java.util.HashMap;

public class Utilities {
    public static final long WAIT = 50;

    public HashMap<String, String> parseStringXML(InputStream file) throws Exception {
        HashMap<String, String> stringMap = new HashMap<>();

        // Get document builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Build document
        Document document = builder.parse(file);

        // Normalize the XML structure
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();

        // Get all elements
        NodeList nodeList = document.getElementsByTagName("string");

        for (int temp = 0; temp < nodeList.getLength(); temp++){
            Node node  = nodeList.item(temp);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;

                // Store each element key-value in Map
                stringMap.put(element.getAttribute("name"),element.getTextContent());
            }
        }

        return stringMap;
    }
}
