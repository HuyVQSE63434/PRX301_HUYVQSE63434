/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.registration;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "tracing", catalog = "BigProject", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tracing.findAll", query = "SELECT t FROM Tracing t")
    , @NamedQuery(name = "Tracing.findByUserId", query = "SELECT t FROM Tracing t WHERE t.tracingPK.userId = :userId")
    , @NamedQuery(name = "Tracing.findByProductId", query = "SELECT t FROM Tracing t WHERE t.tracingPK.productId = :productId")
    , @NamedQuery(name = "Tracing.findByViewTime", query = "SELECT t FROM Tracing t WHERE t.viewTime = :viewTime")
    , @NamedQuery(name = "Tracing.findByLinkTime", query = "SELECT t FROM Tracing t WHERE t.linkTime = :linkTime")
    , @NamedQuery(name = "Tracing.findByPoint", query = "SELECT t FROM Tracing t WHERE t.point = :point")
    , @NamedQuery(name = "Tracing.finMaxPointUser", query = "SELECT t.userInformation.id FROM Tracing t where t.tracingPK.productId = :productId order by t.point asc")
    , @NamedQuery(name = "Tracing.findProductByUser", query = "SELECT t.product from Tracing t where t.tracingPK.userId = :userId order by t.point asc")})
public class Tracing implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TracingPK tracingPK;
    @Basic(optional = false)
    @Column(name = "view_time", nullable = false)
    private int viewTime;
    @Basic(optional = false)
    @Column(name = "link_time", nullable = false)
    private int linkTime;
    @Basic(optional = false)
    @Column(name = "point", nullable = false)
    private int point;
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UserInformation userInformation;

    public Tracing() {
    }

    public Tracing(TracingPK tracingPK) {
        this.tracingPK = tracingPK;
    }

    public Tracing(TracingPK tracingPK, int viewTime, int linkTime, int point) {
        this.tracingPK = tracingPK;
        this.viewTime = viewTime;
        this.linkTime = linkTime;
        this.point = point;
    }

    public Tracing(int userId, String productId) {
        this.tracingPK = new TracingPK(userId, productId);
    }

    public TracingPK getTracingPK() {
        return tracingPK;
    }

    public void setTracingPK(TracingPK tracingPK) {
        this.tracingPK = tracingPK;
    }

    public int getViewTime() {
        return viewTime;
    }

    public void setViewTime(int viewTime) {
        this.viewTime = viewTime;
    }

    public int getLinkTime() {
        return linkTime;
    }

    public void setLinkTime(int linkTime) {
        this.linkTime = linkTime;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tracingPK != null ? tracingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tracing)) {
            return false;
        }
        Tracing other = (Tracing) object;
        if ((this.tracingPK == null && other.tracingPK != null) || (this.tracingPK != null && !this.tracingPK.equals(other.tracingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "huyvq.registration.Tracing[ tracingPK=" + tracingPK + " ]";
    }
    
}
