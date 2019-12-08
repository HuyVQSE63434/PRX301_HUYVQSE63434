/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.parser;

import huyvq.drawl.HashMD5;
import huyvq.registration.Category;
import huyvq.registration.CategoryBLO;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Dell
 */
public class K300CategoryParser extends DefaultHandler {

    private String current;
    private Category dto;
    private CategoryBLO blo;
    private int count;
    public String msg;

    private Map<String, String> links;
    private String currentId;

    public int getCount() {
        return count;
    }

    public String getMessage() {
        return msg;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public K300CategoryParser() {
        blo = new CategoryBLO();
        links = new HashMap<>();
        count = 0;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.current = qName;
        if (qName.equals("category")) {
            dto = new Category();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = new String(ch, start, length);
        switch (current) {
            case "name":
                Category cate = blo.checkCategoryName(s);
                if (cate != null) {
                    dto.setId(cate.getId());
                    dto.setName(cate.getName());
                    currentId = cate.getId();
                } else {
                    dto.setName(s);
                    dto.setId(HashMD5.convertHashToString(s));
                    currentId = dto.getId();
                }
                break;
            case "link":
                links.put(currentId, s);
                currentId = "";
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("category")) {
            if (dto.getId() != null) {
                boolean success = blo.getCategories().add(dto);
                if (success) {
                    count++;
                }
            }
        }
        this.current = "";
    }

    @Override
    public void endDocument() throws SAXException {
        int inserted = blo.insertAllCategory();
        links.remove("1e8c149916e01aab773adbd438934950");
        this.msg = "\nCào được " + count + " danh mục\n";

        this.msg += " Có " + inserted + " danh mục mới\n";

        this.msg += "===============================================================================================";

        this.msg += "";
    }

}
