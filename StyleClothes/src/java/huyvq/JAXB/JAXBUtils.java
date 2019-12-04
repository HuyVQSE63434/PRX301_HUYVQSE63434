/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.JAXB;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.ErrorListener;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Dell
 */
public class JAXBUtils {
    public static void main(String[] args) {
        
        
    }
    
    public static void genObjectByXml(String filename){
        try {
            String output = "src/java";
            SchemaCompiler sc = XJC.createSchemaCompiler();
            sc.setErrorListener(new ErrorListener() {
                
                @Override
                public void error(SAXParseException saxpe) {
                    System.out.println("ERROR: " + saxpe.getMessage());
                }
                
                @Override
                public void fatalError(SAXParseException saxpe) {
                    System.out.println("ERROR: " + saxpe.getMessage());
                }
                
                @Override
                public void warning(SAXParseException saxpe) {
                    System.out.println("ERROR: " + saxpe.getMessage());
                }
                
                @Override
                public void info(SAXParseException saxpe) {
                    System.out.println("ERROR: " + saxpe.getMessage());
                }
            });
            sc.forcePackageName("huyvq.object");
            File schema = new File("web/WEB-INF/xsd/"+filename);
            InputSource is = new InputSource(schema.toURI().toString());
            sc.parseSchema(is);
            S2JJAXBModel model = sc.bind();
            JCodeModel code = model.generateCode(null, null);
            code.build(new File(output));
            System.out.println("Finished");
        } catch (IOException ex) {
            Logger.getLogger(JAXBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void jaxbObjectToXML(Object o) 
    {
        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            
 
            //Print XML String to Console
            StringWriter sw = new StringWriter();
             
            //Write XML to StringWriter
            jaxbMarshaller.marshal(o, sw);
             
            //Verify XML Content
            String xmlContent = sw.toString();
            System.out.println( xmlContent );
 
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
