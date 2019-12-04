/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.registration;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Dell
 */
public class MixColorBLO {

    public MixColorBLO() {
    }
    
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
    
    public List<MixColor> findMixColor(String mainColorId){
        EntityManager em = emf.createEntityManager();
        List<MixColor> mixs = em.createNamedQuery("MixColor.findByMainColorId").setParameter("mainColorId", mainColorId).getResultList();
        return mixs;
    }
    
}
