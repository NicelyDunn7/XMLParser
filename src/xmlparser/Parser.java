/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparser;

import java.io.File;
import javafx.scene.control.TreeItem;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parser class that takes an XML Document and parses it to build a DOM
 * @author daniel
 */
public class Parser extends DefaultHandler {
    
    static TreeItem<String> XMLRoot = new TreeItem();
    
    /**
     * Static method that builds a tree by parsing an XML document
     * @param XMLFile the XML file to be parsed into a DOM
     * @return TreeItem<String> the root of the TreeItem structure
     */
    static TreeItem<String> parser(File XMLFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(XMLFile, new Parser());
            
            // Retrieve the first child TreeItem to be returned as the root
            TreeItem<String> item = XMLRoot.getChildren().get(0);
            XMLRoot.getChildren().clear();
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        TreeItem item = new TreeItem<String>(qName);
        XMLRoot.getChildren().add(item);
        XMLRoot = item;
    }
    
    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        XMLRoot = XMLRoot.getParent();
    }
    
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        String data = String.valueOf(ch, start, length).trim();
        if (!data.isEmpty()) {
            XMLRoot.getChildren().add(new TreeItem<String>(data));
        }
    }
}
