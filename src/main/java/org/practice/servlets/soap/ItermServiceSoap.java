package org.practice.servlets.soap;

import org.practice.servlets.entity.Item;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ItermServiceSoap {

    @WebMethod
    Item getItemById(Long id);
}
