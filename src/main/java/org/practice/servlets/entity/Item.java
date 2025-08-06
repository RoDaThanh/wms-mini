package org.practice.servlets.entity;


import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private String SKU;
    private String name;
    private String quantity;
    private String location;
}
