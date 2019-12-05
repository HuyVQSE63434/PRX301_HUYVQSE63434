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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "user_information", catalog = "BigProject", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserInformation.findAll", query = "SELECT u FROM UserInformation u")
    , @NamedQuery(name = "UserInformation.findById", query = "SELECT u FROM UserInformation u WHERE u.id = :id")
    , @NamedQuery(name = "UserInformation.findByUserName", query = "SELECT u FROM UserInformation u WHERE u.userName = :userName")
    , @NamedQuery(name = "UserInformation.findByPassword", query = "SELECT u FROM UserInformation u WHERE u.password = :password")
    , @NamedQuery(name = "UserInformation.findByFullName", query = "SELECT u FROM UserInformation u WHERE u.fullName = :fullName")
    , @NamedQuery(name = "UserInformation.findByAddress", query = "SELECT u FROM UserInformation u WHERE u.address = :address")
    , @NamedQuery(name = "UserInformation.findByEmail", query = "SELECT u FROM UserInformation u WHERE u.email = :email")
    , @NamedQuery(name = "UserInformation.findByPhoneNumber", query = "SELECT u FROM UserInformation u WHERE u.phoneNumber = :phoneNumber")
    , @NamedQuery(name = "UserInformation.checkLogin", query = "SELECT u FROM UserInformation u WHERE u.userName = :username and u.password = :password")})
public class UserInformation implements Serializable {

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;
    @Basic(optional = false)
    @Column(name = "password", nullable = false, length = 50)
    private String password;
    @Basic(optional = false)
    @Column(name = "full_name", nullable = false, length = 1073741823)
    private String fullName;
    @Column(name = "address", length = 1073741823)
    private String address;
    @Column(name = "email", length = 50)
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userInformation")
    private Collection<Tracing> tracingCollection;

    public UserInformation() {
    }

    public UserInformation(Integer id) {
        this.id = id;
    }

    public UserInformation(Integer id, String userName, String password, String fullName) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (!(object instanceof UserInformation)) {
            return false;
        }
        UserInformation other = (UserInformation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "huyvq.registration.UserInformation[ id=" + id + " ]";
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
}
