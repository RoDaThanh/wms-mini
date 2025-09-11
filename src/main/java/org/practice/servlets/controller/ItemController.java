package org.practice.servlets.controller;

import org.practice.servlets.entity.Item;
import org.practice.servlets.service.ItemService;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class ItemController {

    @EJB
    private ItemService itemService;

    @GET
    @Path("items/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item getItem(@PathParam("id") Long id) {
        return itemService.getItemById(id);
    }

    @GET
    @Path("items")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItems() {
        return itemService.findAll();
    }
}
