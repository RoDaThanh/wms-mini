package org.practice.servlets.service;

import org.practice.servlets.entity.Item;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ItemService {

    @PersistenceContext(unitName = "elmsPU")
    private EntityManager em;

    public void addItem(Item item) {
        em.persist(item);
    }

    public List<Item> findAll() {
        return em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
    }

    public Item getItemById(Long id) {
        return em.find(Item.class, id);
    }
}
