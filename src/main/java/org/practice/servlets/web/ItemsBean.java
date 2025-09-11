package org.practice.servlets.web;

import org.practice.servlets.entity.Item;
import org.practice.servlets.service.ItemService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ItemsBean {

    @EJB
    private ItemService itemService;

    public List<Item> getItems() {
        return itemService.findAll();
    }

    public String viewDetails(Long itemId) {
        return "item_detail.xhtml?faces-redirect=true&id=" + itemId;
    }
}
