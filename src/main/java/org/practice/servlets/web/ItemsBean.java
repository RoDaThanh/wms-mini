package org.practice.servlets.web;

import org.practice.servlets.entity.Item;
import org.practice.servlets.service.ItemService;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.util.List;

@Named
@RequestScoped
public class ItemsBean {

    private Item item = new Item();

    @Resource(lookup = "java:/jms/queue/myQ")
    private Queue itemQueue;

    @Inject
    private JMSContext jmsContext;

    @EJB
    private ItemService itemService;

    public List<Item> getItems() {
        return itemService.findAll();
    }

    public String viewDetails(Long itemId) {
        return "item_detail.xhtml?faces-redirect=true&id=" + itemId;
    }

    public void addItem() {
        String message = item.getName();
        try {
            itemService.addItem(item);
            message = message.concat(" Added successfully!");
        } catch (RuntimeException e) {
            message = message.concat(" Added failed! due to: " + e.getMessage());
        }

        jmsContext.createProducer().send((Destination) itemQueue, message);
    }

    public Item getItem() {
       return this.item;
    }
}
