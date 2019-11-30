/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.registration;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "product", catalog = "BigProject", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id")
    , @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name")
    , @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price")
    , @NamedQuery(name = "Product.findByPicture", query = "SELECT p FROM Product p WHERE p.picture = :picture")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false, length = 50)
    private String id;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 1073741823)
    private String name;
    @Basic(optional = false)
    @Column(name = "price", nullable = false)
    private int price;
    @Basic(optional = false)
    @Column(name = "picture", nullable = false, length = 1073741823)
    private String picture;
    @Basic(optional = false)
    @Column(name = "link", nullable = false, length = 1073741823)
    private String link;
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Category typeId;
    @JoinColumn(name = "color_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Color colorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<Tracing> tracingCollection;

    public Product() {
    }

    public Product(String id) {
        this.id = id;
    }

    public Product(String id, String name, int price, String picture, String link) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    public Category getTypeId() {
        return typeId;
    }

    public void setTypeId(Category typeId) {
        this.typeId = typeId;
    }

    public Color getColorId() {
        return colorId;
    }

    public void setColorId(Color colorId) {
        this.colorId = colorId;
    }

    @XmlTransient
    public Collection<Tracing> getTracingCollection() {
        return tracingCollection;
    }

    public void setTracingCollection(Collection<Tracing> tracingCollection) {
        this.tracingCollection = tracingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "huyvq.registration.Product[ id=" + id + " ]";
    }

    
}
