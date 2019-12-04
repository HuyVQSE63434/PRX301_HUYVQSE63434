
package huyvq.object;

import huyvq.registration.Product;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="product" type="{}Product" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="nextlink" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "product",
    "nextlink"
})
@XmlRootElement(name = "products")
public class Products {

    protected List<huyvq.registration.Product> product;
    @XmlElement(required = true)
    protected String nextlink;

    /**
     * Gets the value of the product property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the product property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProduct().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Product }
     * 
     * 
     */
    public List<huyvq.registration.Product> getProduct() {
        if (product == null) {
            product = new ArrayList<huyvq.registration.Product>();
        }
        return this.product;
    }

    /**
     * Gets the value of the nextlink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNextlink() {
        return nextlink;
    }

    /**
     * Sets the value of the nextlink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextlink(String value) {
        this.nextlink = value;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public Products(List<Product> product) {
        this.product = product;
    }

    public Products() {
    }
    
    

}
