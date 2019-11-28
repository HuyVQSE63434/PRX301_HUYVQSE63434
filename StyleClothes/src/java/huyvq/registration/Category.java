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
@Table(name = "category", catalog = "BigProject", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
    , @NamedQuery(name = "Category.findById", query = "SELECT c FROM Category c WHERE c.id = :id")
    , @NamedQuery(name = "Category.findByName", query = "SELECT c FROM Category c WHERE c.name = :name")
    , @NamedQuery(name = "Category.findByUpper", query = "SELECT c FROM Category c WHERE c.upper = :upper")})
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false, length = 50)
    private String id;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 1073741823)
    private String name;
    @Basic(optional = false)
    @Column(name = "upper", nullable = false)
    private boolean upper;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeId")
    private Collection<Product> productCollection;

    public Category() {
    }

    public Category(String id) {
        this.id = id;
    }

    public Category(String id, String name, boolean upper) {
        this.id = id;
        this.name = name;
        this.upper = upper;
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

    public boolean getUpper() {
        return upper;
    }

    public void setUpper(boolean upper) {
        this.upper = upper;
    }

    @XmlTransient
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
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
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "huyvq.registration.Category[ id=" + id + " ]";
    }
    
}
