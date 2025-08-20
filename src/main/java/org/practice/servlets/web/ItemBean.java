package org.practice.servlets.web;

import org.practice.servlets.entity.Item;
import org.practice.servlets.service.ItemService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ItemBean {
    private String sku;
    private String name;

    @EJB
    private ItemService itemService;

    public List<Item> getItems() {
        return itemService.findAll();
    }

    public void addItem() {
        itemService.addItem(sku, name);
        sku = "";
        name = "";
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
