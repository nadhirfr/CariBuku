package com.fana.caribuku.model;

import java.util.List;

/**
 * Created by FACHRUL on 5/29/2016.
 */


public class Book {
    private String id;
    private String name;
    private String autthor;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id= id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAutthor() {
        return autthor;
    }

    public void setAutthor(String autthor) {
        this.autthor = autthor;
    }
}
