/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.parser;

import huyvq.drawl.HashMD5;
import huyvq.registration.Color;
import huyvq.registration.Product;
import huyvq.registration.ProductBLO;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Dell
 */
public class ProductDetailParser extends DefaultHandler {

    private String current;
    private String color;

    public String getColor() {
        return color;
    }

    public ProductDetailParser() {
        color = "";
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.current = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = new String(ch, start, length);
        switch (current) {
            case "color1":
                if (s.length() > 1) {
                    color = s;
                }
                break;
            case "color2":
                if (s.length()>1 && color.isEmpty()) {
                    color = s;
                }
                break;
            case "soldold":
                if (!s.isEmpty() && color.isEmpty()) {
                    color = "undefined";
                }
            default:
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.current = "";
    }

}
