package org.practice.servlets.web;

import org.practice.servlets.entity.Item;
import org.practice.servlets.service.ItemService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class ItemBean {

    @EJB
    private ItemService itemService;

    private Item item;

    @PostConstruct
    public void init() {
        String idParam = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("id");
        if (idParam != null) {
            Long id = Long.valueOf(idParam);
            item = itemService.getItemById(id);
        }
    }

    public Item getItem() {
        return item;
    }
}
