package org.practice.servlets.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.practice.servlets.entity.Item;
import org.practice.servlets.service.ItemService;
import org.practice.servlets.socket.AsyncServer;
import org.practice.servlets.socket.Message;

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

    @EJB
    private AsyncServer asyncServer;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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
            String jsonMessage = OBJECT_MAPPER.writeValueAsString(new Message(Message.MessageType.ITEM_ADDED));
            asyncServer.broadcast(jsonMessage);
        } catch (RuntimeException e) {
            message = message.concat(" Added failed! due to: " + e.getMessage());
        } catch (JsonProcessingException ex) {
            System.err.println(ex.getMessage());
        }

        jmsContext.createProducer().send((Destination) itemQueue, message);
    }

    public Item getItem() {
        return this.item;
    }
}
