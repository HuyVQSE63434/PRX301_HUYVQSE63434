/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.drawl;

import huyvq.parser.BadhabitCategoryParser;
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
    private String msg = "";

    private static final String badhabit = "badhabitsstore.vn";
    private static final String k300 = "k300shop.com";

    public Crawler(ServletContext context, String url) {
        this.context = context;
        this.url = url;
        this.domain = XMLUtils.getDomain(url);
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
                CrawlBadhabit crBh = new CrawlBadhabit(url, prefix, domain, context);
                msg += crBh.crawlBadhabit();
                break;
            case k300:
                CrawlK300 crK300 = new CrawlK300(url,prefix,domain,context);
                msg += crK300.crawlK300();
                break;
            default:
                return "-1";
        }

        return msg;
    } 
}
