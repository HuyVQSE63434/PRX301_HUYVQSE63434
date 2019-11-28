/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.registration;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Dell
 */
@Embeddable
public class TracingPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Basic(optional = false)
    @Column(name = "product_id", nullable = false, length = 50)
    private String productId;

    public TracingPK() {
    }

    public TracingPK(int userId, String productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TracingPK)) {
            return false;
        }
        TracingPK other = (TracingPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "huyvq.registration.TracingPK[ userId=" + userId + ", productId=" + productId + " ]";
    }
    
}
