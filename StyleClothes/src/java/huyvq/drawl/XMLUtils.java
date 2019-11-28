/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.drawl;

import static com.sun.activation.registries.LogSupport.log;
import huyvq.registration.Product;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Dell
 */
public class XMLUtils {
    
    public static String marshallToString(Product medicines) {
        try {
            JAXBContext jaxbc = JAXBContext.newInstance(Product.class);
            Marshaller ms = jaxbc.createMarshaller();
            ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter sv = new StringWriter();
            
            ms.marshal(medicines, sv);
            
            return sv.toString();
            
        } catch (Exception e) {
            log("Exception: " + e.getMessage());
        }
        return null;
    }
    public static void parseString(String xml, DefaultHandler handler) {
        try {
            StringReader sr = new StringReader(xml);
            InputSource is = new InputSource(sr);
            //tạo đối tượng cung cấp API
            SAXParserFactory spf = SAXParserFactory.newInstance();
            //
            SAXParser sax = spf.newSAXParser();
            //parse and process
            sax.parse(is, handler);
        } catch (ParserConfigurationException   e) {
            log("Parser " + e.getMessage());
        } catch (SAXException e) {
            log("SAX " + e.getMessage());
        } catch (IOException e) {
            log("IO " + e.getMessage());
        }
    }
    
    public static String getDomain(String url) {
        String domain = url;
        try {
            domain = domain.substring(domain.indexOf("//") + 2);
            domain = domain.substring(0, domain.indexOf("/"));
        } catch (Exception e) {
            log("Exception " + e.getMessage());
        }
        return domain;
    }
    
    public static String getSubDomain(String url) {
        String domain = url;
        domain = domain.substring(domain.indexOf("//") + 2);
        domain = domain.substring(domain.indexOf("/"));
        return domain;
    }

}
