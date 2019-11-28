/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.drawl;

import static com.sun.activation.registries.LogSupport.log;
import huyvq.connection.RegexProcess;
import java.io.IOException;
import java.io.StringReader;
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
public class Crawler {

    private ServletContext context;

    private String url;
    private String prefix;
    private String domain;
    private String subdomain;
    private String msg = "";

    private static final String badhabit = "badhabitsstore.vn";
    private static final String k300 = "k300shop.com";

    public Crawler(ServletContext context, String url) {
        this.context = context;
        this.url = url;
        this.domain = XMLUtils.getDomain(url);
        this.subdomain = XMLUtils.getSubDomain(url);
        while (subdomain.endsWith("/")) {
            subdomain = subdomain.substring(0, subdomain.length() - 1);
        }
        this.prefix = "https://" + domain + "/";
    }

    public String crawl() {
        System.out.println("Đang cào: " + url);
        System.out.println("Processing...............");
        System.out.println("Prefix: " + prefix);
        System.out.println("Domain: " + domain);
        //crawlSUCKHOE();
        switch (domain) {
            case badhabit:
                msg += crawlBadhabit();
                break;
            case k300:
                msg += crawlK300();
                break;
            default:
                return "-1";
        }

        return msg;
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
        return getData(content);
    }

    public String crawlK300() {
        String content = new RegexProcess(url)
                .access()
                .match("<body[\\s\\S]*?>[\\s\\S]*?<\\/body>")
                //.match("<div class='row'>[\\s\\S]*?</div>")
                .clean("<script[\\s\\S]*?>[\\s\\S]*?<\\/script>")
                .clean("<noscript>[\\s\\S]*?</noscript>")
                .clean("\n")
                //.clean("<!-- [\\s\\S]*? -->")
                // .replace("&", "&amp;")
                .toString();
        System.out.println("Body: " + content);
        return getData(content);
    }

    public String getXSLPath(String filename) {
        String realPath = context.getRealPath("/");
        return realPath + "/WEB-INF/xsl/" + filename;
    }

    public String getXSDPath(String filename) {
        String realPath = context.getRealPath("/");
        return realPath + "/WEB-INF/xsd/" + filename;
    }

    private int count = 0;
    public String getData(String xmlRaw) {
        try {
        String xsl = getXSLPath(domain + ".xsl");
        //transform thành xml
        String xml = Transform.transform(xsl, xmlRaw);
        System.out.println("XML before validate: " + xml);
        //validate trước khi đưa vào database
        //xml = validate(xml);
        System.out.println("XML: " + xml);
        if (xml == null) {
            return "0";
        }
        //insert vào database 
        Parser pasrser = new Parser();
        XMLUtils.parseString(xml, pasrser);
        
        //return pasrser.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String validate(String xml) {
        StringReader sr = new StringReader(xml);
        StreamSource xmlSource = new StreamSource(sr);
        StreamSource xsd = new StreamSource(getXSDPath("products.xsd"));
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            //xml schema định dạng dữ liệu
            Schema schema = sf.newSchema(xsd);
            Validator validator = schema.newValidator();
            //validate
            validator.validate(xmlSource);
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

}
