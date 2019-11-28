/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.drawl;

import static com.sun.activation.registries.LogSupport.log;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Dell
 */
public class Transform {
    
     public static interface CustomizeTransformerCallback {
        public void customize(Transformer trans);
    }
     
     
     
    public static String transform(String xslpath, String xmlContent) {
        return transforms(xslpath, xmlContent, null);
    }
    
    public static String transforms(String xslpath, String xmlContent, CustomizeTransformerCallback callback) {
        try {
            StringReader reader = new StringReader(xmlContent);
            StringWriter writer = new StringWriter();
            
            StreamSource src = new StreamSource(reader);
            StreamResult res = new StreamResult(writer);
            StreamSource xsl = new StreamSource(xslpath);
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer trans = tf.newTransformer(xsl);
            
            if (callback != null) {
                callback.customize(trans);
            }
            trans.transform(src, res);
            return res.getWriter().toString();
        } catch (TransformerException ex) {
            log("Parser " + ex.getMessage());
            return null;
        }
    }
    
   
}
