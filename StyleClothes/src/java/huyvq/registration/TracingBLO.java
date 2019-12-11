/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.registration;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Dell
 */
public class TracingBLO implements Serializable {

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

    public void countingViewTime(String id, int userid) {
        EntityManager em = emf.createEntityManager();
        try {
            Tracing tra = (Tracing) em.createNamedQuery("Tracing.findByProductIdAndUserId").setParameter("userId", userid).setParameter("productId", id).getSingleResult();
            tra.setViewTime(tra.getViewTime() + 1);
            tra.setPoint(tra.getViewTime() + tra.getLinkTime() * 2);
            em.getTransaction().begin();
            em.merge(tra);
            em.getTransaction().commit();
        } catch (Exception e) {
            Tracing tra = new Tracing();
            UserInformation user = em.find(UserInformation.class, userid);
            tra.setUserInformation(user);
            Product pro = em.find(Product.class, id.trim());
            tra.setTracingPK(new TracingPK(userid, id));
            tra.setProduct(pro);
            tra.setViewTime(1);
            tra.setLinkTime(0);
            tra.setPoint(tra.getViewTime() + tra.getLinkTime() * 2);
            em.getTransaction().begin();
            em.persist(tra);
            em.getTransaction().commit();
        }
    }

    public void countingLinkTime(String productId, int userid) {
        EntityManager em = emf.createEntityManager();
        try {
            Tracing tra = (Tracing) em.createNamedQuery("Tracing.findByProductIdAndUserId").setParameter("userId", userid).setParameter("productId", productId).getSingleResult();
            tra.setLinkTime(tra.getLinkTime()+ 1);
            tra.setPoint(tra.getViewTime() + tra.getLinkTime() * 2);
            em.getTransaction().begin();
            em.merge(tra);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
