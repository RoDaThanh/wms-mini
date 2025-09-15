package org.practice.servlets.soap;

import org.practice.servlets.entity.Item;
import org.practice.servlets.service.ItemService;

import javax.inject.Inject;
import javax.jws.WebService;

@WebService
public class ItermServiceSoapImpl implements ItermServiceSoap {

    @Inject
    private ItemService itemService;

    @Override
    public Item getItemById(Long id) {
        return itemService.getItemById(id);
    }
}
