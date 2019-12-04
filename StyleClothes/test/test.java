

import huyvq.registration.Product;
import huyvq.registration.ProductBLO;
import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Dell
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        System.out.println(computeMatchingPercent("PANTS", "SHORTS/PANTS"));
//        EntityManager em = emf.createEntityManager();
//        Categories a = new Categories();
//        a.setCategory(em.createQuery("SELECT a FROM Category a").getResultList());
//        jaxbObjectToXML(a);
//        Products pros = new Products();
//        pros.setProduct(em.createQuery("SELECT b FROM Product b ").getResultList().subList(0, 10));
//        jaxbObjectToXML(pros);
        
    }

    public static int computeMatchingPercent(String a, String b) {
        int n = a.length();
        int m = b.length();

        int dp[][] = new int[n + 1][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (a.charAt(i) == b.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }
        return (int) dp[n][m] * 100 / Math.min(m, m);
    }

    private static void jaxbObjectToXML(Object o) {
        try {
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
            System.out.println(xmlContent + "\n");

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
