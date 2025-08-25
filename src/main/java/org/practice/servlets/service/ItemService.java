package org.practice.servlets.service;

import org.practice.servlets.entity.Item;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ItemService {

    @PersistenceContext(unitName = "elmsPU")
    private EntityManager em;

    private List<Item> itemsMock = new ArrayList<>();


    public Item addItem(String sku, String name) {
        Item item = new Item();
        item.setSKU(sku);
        item.setName(name);
//        em.persist(item);
        itemsMock.add(item);
        return item;
    }

    public List<Item> findAll() {
        return itemsMock;
//        return em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
    }
}
