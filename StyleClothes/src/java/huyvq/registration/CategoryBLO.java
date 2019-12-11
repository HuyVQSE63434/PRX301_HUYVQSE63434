/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.registration;

import huyvq.drawl.XMLUtils;
import java.io.Serializable;
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
public class CategoryBLO implements Serializable{

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
                if (!category.getName().equalsIgnoreCase("new product") && !category.getName().equalsIgnoreCase("backpack")
                        && !category.getName().equalsIgnoreCase("BAD RABBIT")
                        && !category.getName().equalsIgnoreCase("CLOTHING")) {
                    em.getTransaction().begin();
                    em.persist(category);
                    em.getTransaction().commit();
                    count++;
                }
            }
        }
        return count;
    }

    public Category checkCategoryName(String name) {
        EntityManager em = emf.createEntityManager();
        List<Category> list = em.createQuery("SELECT r FROM Category r").getResultList();
        for (Category category1 : list) {
            if (category1.getName().contains("/")) {
                String[] sps = category1.getName().split("/");
                for (String sp : sps) {
                    int check = XMLUtils.computeMatchingPercent(sp, name);
                    if (check >= 80) {
                        return category1;
                    }
                }
            } else {
                int check = XMLUtils.computeMatchingPercent(category1.getName(), name);
                if (check > 85) {
                    return category1;
                }
            }
        }
        return null;
    }

    public List<Category> getAllCategories() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Category> cateList = (List<Category>) em.createNamedQuery("Category.findAll").getResultList();
            return cateList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
