package org.practice.servlets.service;

import org.practice.servlets.entity.Item;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Stateless
public class ItemService {

    @PersistenceContext(unitName = "elmsPU")
    private EntityManager em;

    public void addItem(String sku, String name) {
        Item item = new Item();
        item.setSKU(sku);
        item.setName(name);
        em.persist(item);
    }

    public List<Item> findAll() {
        return em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
    }

    public Item getItemById(Long id) {
        Item item = new Item();

        if (Objects.nonNull(id) && id == 1) {
            item.setSKU("mockSKU");
            item.setName("mockName");
            return item;
        }
        return null;
    }
}
