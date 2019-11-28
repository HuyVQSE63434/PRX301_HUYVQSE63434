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
@Table(name = "mix_color", catalog = "BigProject", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MixColor.findAll", query = "SELECT m FROM MixColor m")
    , @NamedQuery(name = "MixColor.findByMainColorId", query = "SELECT m FROM MixColor m WHERE m.mixColorPK.mainColorId = :mainColorId")
    , @NamedQuery(name = "MixColor.findByMixColorId", query = "SELECT m FROM MixColor m WHERE m.mixColorPK.mixColorId = :mixColorId")
    , @NamedQuery(name = "MixColor.findByPriority", query = "SELECT m FROM MixColor m WHERE m.priority = :priority")})
public class MixColor implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MixColorPK mixColorPK;
    @Basic(optional = false)
    @Column(name = "priority", nullable = false)
    private int priority;
    @JoinColumn(name = "main_color_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Color color;
    @JoinColumn(name = "mix_color_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Color color1;

    public MixColor() {
    }

    public MixColor(MixColorPK mixColorPK) {
        this.mixColorPK = mixColorPK;
    }

    public MixColor(MixColorPK mixColorPK, int priority) {
        this.mixColorPK = mixColorPK;
        this.priority = priority;
    }

    public MixColor(String mainColorId, String mixColorId) {
        this.mixColorPK = new MixColorPK(mainColorId, mixColorId);
    }

    public MixColorPK getMixColorPK() {
        return mixColorPK;
    }

    public void setMixColorPK(MixColorPK mixColorPK) {
        this.mixColorPK = mixColorPK;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mixColorPK != null ? mixColorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MixColor)) {
            return false;
        }
        MixColor other = (MixColor) object;
        if ((this.mixColorPK == null && other.mixColorPK != null) || (this.mixColorPK != null && !this.mixColorPK.equals(other.mixColorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "huyvq.registration.MixColor[ mixColorPK=" + mixColorPK + " ]";
    }
    
}
