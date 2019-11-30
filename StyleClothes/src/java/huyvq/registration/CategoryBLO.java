/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.registration;

import java.util.ArrayList;
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
public class CategoryBLO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("StyleClothesPU");
    private List<Category> categories;

    public CategoryBLO() {
        categories = new ArrayList<>();
    }

    public List<Category> getCategories() {
        return categories;
    }

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

    public int insertAllCategory() {
        EntityManager em = emf.createEntityManager();
        int count = 0;
        for (Category category : categories) {
            Category c = em.find(Category.class, category.getId());
            if (c == null) {
                if (!category.getName().equalsIgnoreCase("new product")) {
                    em.getTransaction().begin();
                    em.persist(category);
                    em.getTransaction().commit();
                    count++;
                }
            }
        }
        return count;
    }
}
