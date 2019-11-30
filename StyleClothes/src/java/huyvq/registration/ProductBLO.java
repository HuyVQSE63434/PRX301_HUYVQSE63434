/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.registration;

import huyvq.drawl.HashMD5;
import huyvq.drawl.XMLUtils;
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
public class ProductBLO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("StyleClothesPU");
    private List<Product> products;

    public ProductBLO() {
        products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
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
    
    public int insertAllProducts() {
        EntityManager em = emf.createEntityManager();
        int count = 0;
        for (Product category : products) {
            Product c = em.find(Product.class, category.getId());
            if (c == null) {
                em.getTransaction().begin();
                em.persist(category);
                em.getTransaction().commit();
                count++;
            }
        }
        return count;
    }
    
    public Product addColor(String color, Product dto){
        EntityManager em = emf.createEntityManager();
        Color cl = (Color) em.createQuery("SELECT r FROM color r WHERE r.vietnamName = :color OR r.englishName= :color").setParameter("color", color).getSingleResult();
        dto.setColorId(cl);
        return dto;
    }

    public int insertAllProducts(String typeId) {
        EntityManager em = emf.createEntityManager();
        int count = 0;
        for (Product product : products) {
            List c = em.createQuery("SELECT r FROM Product r WHERE r.picture= :picture").setParameter("picture", product.getPicture()).getResultList();
            if (c.size()==0) {
                product.setTypeId(em.find(Category.class, typeId));
                if(product.getColorId()==null) addColor("đen", product);
                em.getTransaction().begin();
                em.persist(product);
                em.getTransaction().commit();
                count++;
            }
        }
        return count;
    }

}
