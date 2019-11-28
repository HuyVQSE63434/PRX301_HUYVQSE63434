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
@Table(name = "color", catalog = "BigProject", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Color.findAll", query = "SELECT c FROM Color c")
    , @NamedQuery(name = "Color.findById", query = "SELECT c FROM Color c WHERE c.id = :id")
    , @NamedQuery(name = "Color.findByVietnamName", query = "SELECT c FROM Color c WHERE c.vietnamName = :vietnamName")
    , @NamedQuery(name = "Color.findByEnglishName", query = "SELECT c FROM Color c WHERE c.englishName = :englishName")})
public class Color implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false, length = 10)
    private String id;
    @Basic(optional = false)
    @Column(name = "vietnam_name", nullable = false, length = 50)
    private String vietnamName;
    @Basic(optional = false)
    @Column(name = "english_name", nullable = false, length = 50)
    private String englishName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "colorId")
    private Collection<Product> productCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "color")
    private Collection<MixColor> mixColorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "color1")
    private Collection<MixColor> mixColorCollection1;

    public Color() {
    }

    public Color(String id) {
        this.id = id;
    }

    public Color(String id, String vietnamName, String englishName) {
        this.id = id;
        this.vietnamName = vietnamName;
        this.englishName = englishName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVietnamName() {
        return vietnamName;
    }

    public void setVietnamName(String vietnamName) {
        this.vietnamName = vietnamName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @XmlTransient
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    @XmlTransient
    public Collection<MixColor> getMixColorCollection() {
        return mixColorCollection;
    }

    public void setMixColorCollection(Collection<MixColor> mixColorCollection) {
        this.mixColorCollection = mixColorCollection;
    }

    @XmlTransient
    public Collection<MixColor> getMixColorCollection1() {
        return mixColorCollection1;
    }

    public void setMixColorCollection1(Collection<MixColor> mixColorCollection1) {
        this.mixColorCollection1 = mixColorCollection1;
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
        if (!(object instanceof Color)) {
            return false;
        }
        Color other = (Color) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "huyvq.registration.Color[ id=" + id + " ]";
    }
    
}
