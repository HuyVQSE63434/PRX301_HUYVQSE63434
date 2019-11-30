/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.drawl;

import static com.sun.activation.registries.LogSupport.log;
import huyvq.parser.CategoryParser;
import huyvq.parser.ProductDetailParser;
import huyvq.parser.ProductParser;
import huyvq.registration.Product;
import huyvq.registration.ProductBLO;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.servlet.ServletContext;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

/**
 *
 * @author Dell
 */
public class CrawlBadhabit {

    private String url;
    private String prefix;
    private String domain;
    private ServletContext context;

    public CrawlBadhabit(String url, String prefix, String domain, ServletContext context) {
        this.url = url;
        this.prefix = prefix;
        this.domain = domain;
        this.context = context;
    }

    public String crawlBadhabit() {
        String content = new RegexProcess(url)
                .access()
                .match("<body[\\s\\S]*?>[\\s\\S]*?<\\/body>")
                //.match("<div class='row'>[\\s\\S]*?</div>")
                .clean("<script[\\s\\S]*?>[\\s\\S]*?<\\/script>")
                .clean("<noscript>[\\s\\S]*?</noscript>")
                .clean("<g>[\\s\\S]*?</g>")
                .replace("</symboy>", "</symbol>")
                .clean("<symbol[\\s\\S]*?>[\\s\\S]*?<\\/symbol>")
                .clean("<svg[\\s\\S]*?>[\\s\\S]*?<\\/svg>")
                .clean("<g[\\s\\S]*?>[\\s\\S]*?<\\/g>")
                //.clean("<!-- [\\s\\S]*? -->")
                // .replace("&", "&amp;")
                .toString();
        content = XMLUtils.check(content);
        System.out.println("Body: " + content);
        return getDataCategory(content);
    }

    private int count = 0;

    public String getDataCategory(String xmlRaw) {
        try {
            String xsl = getXSLPath(domain + ".xsl");
            //transform thành xml
            String xml = Transform.transform(xsl, xmlRaw);
            System.out.println("XML before validate: " + xml);
            //validate trước khi đưa vào database
            xml = validateCategory(xml);
            System.out.println("XML: " + xml);
            if (xml == null) {
                return "0";
            }
            //insert vào database 
            CategoryParser pasrser = new CategoryParser();
            XMLUtils.parseString(xml, pasrser);
            System.out.println(pasrser.getMessage());
            Map<String, String> links = pasrser.getLinks();
            String message = null;
            for (Map.Entry<String, String> entry : links.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                String cataLink = prefix + value;
                System.out.println("catagory link: " + cataLink);
                System.out.println("key-value:" + key + " " + value);
                message = getProduct(cataLink, key);
            }
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String validateCategory(String xml) {
        StringReader sr = new StringReader(xml);
        StreamSource xmlSource = new StreamSource(sr);
        StreamSource xsd = new StreamSource(getXSDPath("badhabitcategory.xsd"));
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            //xml schema định dạng dữ liệu
            Schema schema = sf.newSchema(xsd);
            Validator validator = schema.newValidator();
            //validate
            validator.validate(xmlSource);
            System.out.println("validate category badhabit success");
            return xml;
        } catch (SAXException e) {
            log("SAX" + e.getMessage());
            //    return null;
        } catch (IOException e) {
            log("IOException " + e.getMessage());
            //    return null;
        }
        return null;
    }

    private String getContent(String link) {
        String content = new RegexProcess(link)
                .access()
                .match("<body[\\s\\S]*?>[\\s\\S]*?<\\/body>")
                //.match("<div class='row'>[\\s\\S]*?</div>")
                .clean("<script[\\s\\S]*?>[\\s\\S]*?<\\/script>")
                .clean("<noscript>[\\s\\S]*?</noscript>")
                .clean("<g>[\\s\\S]*?</g>")
                .replace("</symboy>", "</symbol>")
                .clean("<symbol[\\s\\S]*?>[\\s\\S]*?<\\/symbol>")
                .clean("<svg[\\s\\S]*?>[\\s\\S]*?<\\/svg>")
                .clean("<g[\\s\\S]*?>[\\s\\S]*?<\\/g>")
                //.clean("<!-- [\\s\\S]*? -->")
                // .replace("&", "&amp;")
                .toString();
        content = XMLUtils.check(content);
        return content;
    }

    private String getProduct(String cataLink, String key) {
        System.out.println("truy cập vào " + cataLink);
        String content = getContent(cataLink);
        System.out.println("Body: " + content);
        return getDataProduct(content, cataLink, key);
    }

    private String getDataProduct(String xmlRaw, String cataLink, String key) {
        try {
            String xsl = getXSLPath("products.xsl");
            //transform thành xml
            String xml = Transform.transform(xsl, xmlRaw);
            System.out.println("XML before validate: " + xml);
            //validate trước khi đưa vào database
            xml = validateProductBh(xml, "products.xsd");
            System.out.println("XML after validate: " + xml);
            if (xml == null) {
                return "0";
            }
            //insert vào database 
            ProductParser parser = new ProductParser(cataLink, key);
            XMLUtils.parseString(xml, parser);
            List<Product> products = parser.getListProducts();
            products = addColorAndInsertProduct(products, key);
            String nextLink = parser.getNextLink();
            String urlNexkLink = this.prefix + nextLink;
            System.out.println(urlNexkLink);
            return (nextLink != null) ? getProduct(urlNexkLink, key) : "crawl xong link" + cataLink;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String validateProductBh(String xml, String xsdName) {
        StringReader sr = new StringReader(xml);
        StreamSource xmlSource = new StreamSource(sr);
        try {
            StreamSource xsd = new StreamSource(getXSDPath(xsdName));
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            //xml schema định dạng dữ liệu
            Schema schema = sf.newSchema(xsd);
            Validator validator = schema.newValidator();
            //validate
            validator.validate(xmlSource);
            System.out.println("validate product badhabit success");
            return xml;
        } catch (SAXException e) {
            System.out.println("error sax: " + e.getMessage());
            //    return null;
        } catch (IOException e) {
            System.out.println("error IO: " + e.getMessage());
            //    return null;
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
        return null;
    }

    public String getXSLPath(String filename) {
        String realPath = context.getRealPath("/");
        return realPath + "/WEB-INF/xsl/" + filename;
    }

    public String getXSDPath(String filename) {
        String realPath = context.getRealPath("/");
        return realPath + "WEB-INF/xsd/" + filename;
    }

    private List addColorAndInsertProduct(List<Product> products, String key) {
        ProductBLO blo = new ProductBLO(products);
        for (Product product : products) {
            String link = product.getLink();
            System.out.println("access link " + link);
            String content = getContent(link);
            System.out.println("Body: " + content);
            String xsl = getXSLPath("productDetails.xsl");
            //transform thành xml
            String xml = Transform.transform(xsl, content);
            System.out.println("XML before validate: " + xml);
            xml = validateProductBh(xml, "productDetails.xsd");
            System.out.println("XML after validate: " + xml);
            ProductDetailParser parser = new ProductDetailParser();
            XMLUtils.parseString(xml, parser);
            blo.addColor(parser.getColor(), product);
        }
        blo.setProducts(products);
        blo.insertAllProducts(key);
        return products;
    }

}
