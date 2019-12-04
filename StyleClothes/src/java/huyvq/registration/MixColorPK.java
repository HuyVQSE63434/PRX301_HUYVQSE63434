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
public class MixColorPK implements Serializable{

    @Basic(optional = false)
    @Column(name = "main_color_id", nullable = false, length = 10)
    private String mainColorId;
    @Basic(optional = false)
    @Column(name = "mix_color_id", nullable = false, length = 10)
    private String mixColorId;

    public MixColorPK() {
    }

    public MixColorPK(String mainColorId, String mixColorId) {
        this.mainColorId = mainColorId;
        this.mixColorId = mixColorId;
    }

    public String getMainColorId() {
        return mainColorId;
    }

    public void setMainColorId(String mainColorId) {
        this.mainColorId = mainColorId;
    }

    public String getMixColorId() {
        return mixColorId;
    }

    public void setMixColorId(String mixColorId) {
        this.mixColorId = mixColorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mainColorId != null ? mainColorId.hashCode() : 0);
        hash += (mixColorId != null ? mixColorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MixColorPK)) {
            return false;
        }
        MixColorPK other = (MixColorPK) object;
        if ((this.mainColorId == null && other.mainColorId != null) || (this.mainColorId != null && !this.mainColorId.equals(other.mainColorId))) {
            return false;
        }
        if ((this.mixColorId == null && other.mixColorId != null) || (this.mixColorId != null && !this.mixColorId.equals(other.mixColorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "huyvq.registration.MixColorPK[ mainColorId=" + mainColorId + ", mixColorId=" + mixColorId + " ]";
    }
    
}
