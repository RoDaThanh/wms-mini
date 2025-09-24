package org.practice.servlets.service;

import org.practice.servlets.entity.Item;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class ItemService {

    @PersistenceContext(unitName = "elmsPU")
    private EntityManager em;

    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item must not be null");
        }

        if (item.getSKU().isEmpty()) {
            throw new IllegalArgumentException("SKU must not be empty");
        }

        if (item.getName().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty");
        }

        try {
            if (item.getCreatedAt() == null) {
                item.setCreatedAt(LocalDateTime.now());
            }
            em.persist(item);
            em.flush();
        } catch (Exception e) {
            // Convert low-level DB exception to business-level exception
            throw new RuntimeException(
                    String.format("Failed to persist Item with SKU '%s': %s", item.getSKU(), e.getMessage()), e.getCause()
            );
        }
    }

    public List<Item> findAll() {
        return em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
    }

    public Item getItemById(Long id) {
        return em.find(Item.class, id);
    }
}
