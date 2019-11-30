/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.parser;

import huyvq.drawl.HashMD5;
import huyvq.registration.Product;
import huyvq.registration.ProductBLO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Dell
 */
public class ProductParser extends DefaultHandler {

    private String current;
    private Product dto;
    private int count;
    public String fatherLink;
    public String typeId;
    private String nextLink;
    
    private List<Product> listProducts;

    public ProductParser(String cataLink, String key) {
        listProducts = new ArrayList<>();
        count = 0;
        fatherLink = cataLink;
        typeId = key;

    }

    public List<Product> getListProducts() {
        return listProducts;
    }
    
    public String getNextLink() {
        return nextLink;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.current = qName;
        if (qName.equals("product")) {
            dto = new Product();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = new String(ch, start, length);
        switch (current) {
            case "name":
                dto.setName(s);
                break;
            case "price":
                s = s.replaceAll("[^0-9,-\\.]", ",");
                String[] list = s.split(",");
                dto.setPrice(Integer.valueOf(list[0] + list[1]));
                break;
            case "picture":
                dto.setPicture(s);
                dto.setId(HashMD5.convertHashToString(s));
                break;
            case "link":
                dto.setLink(fatherLink + s);
                break;
            case "nextlink":
                this.nextLink = s;
                break;
            default:
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("product")) {
            boolean success = listProducts.add(dto);
            if (success) {
                count++;
            }
        }
        this.current = "";
    }

    @Override
    public void endDocument() throws SAXException {
    }

}
