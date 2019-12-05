/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.registration;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Dell
 */
public class UserBLO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("StyleClothesPU");

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public UserInformation checkLogin(String username, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            UserInformation user = (UserInformation) em.createNamedQuery("UserInformation.checkLogin").setParameter("username", username).setParameter("password", password).getSingleResult();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserInformation checkUserName(String userName) {
        EntityManager em = emf.createEntityManager();
        try {
            UserInformation user = (UserInformation) em.createNamedQuery("UserInformation.findByUserName").setParameter("userName", userName).getSingleResult();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean AddNewUser(UserInformation user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserBLO() {
    }

}
