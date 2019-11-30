/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.drawl;

import huyvq.parser.CategoryParser;
import static com.sun.activation.registries.LogSupport.log;
import huyvq.parser.ProductParser;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
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
        this.prefix = "https://" + domain;
    }

    public String crawl() {
        System.out.println("Đang cào: " + url);
        System.out.println("Processing...............");
        System.out.println("Prefix: " + prefix);
        System.out.println("Domain: " + domain);
        //crawlSUCKHOE();
        switch (domain) {
            case badhabit:
                CrawlBadhabit cr = new CrawlBadhabit(url, prefix, domain, context);
                msg += cr.crawlBadhabit();
                break;
            case k300:
                //msg += crawlK300();
                break;
            default:
                return "-1";
        }

        return msg;
    }

//        public String crawlK300() {
//        String content = new RegexProcess(url)
//                .access()
//                .match("<body[\\s\\S]*?>[\\s\\S]*?<\\/body>")
//                //.match("<div class='row'>[\\s\\S]*?</div>")
//                .clean("<script[\\s\\S]*?>[\\s\\S]*?<\\/script>")
//                .clean("<noscript>[\\s\\S]*?</noscript>")
//                .clean("\n")
//                //.clean("<!-- [\\s\\S]*? -->")
//                // .replace("&", "&amp;")
//                .toString();
//        System.out.println("Body: " + content);
//        return getDataCategory(content);
//    }
//
//    
//    public String getXSLPath(String filename) {
//        String realPath = context.getRealPath("/");
//        return realPath + "/WEB-INF/xsl/" + filename;
//    }
//
//    public String getXSDPath(String filename) {
//        String realPath = context.getRealPath("/");
//        return realPath + "WEB-INF/xsd/" + filename;
//    }
//
//   
}
