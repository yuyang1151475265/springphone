package com.jk.entity;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

public class Solr implements Serializable {

    @Field
    private String id;
    @Field
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Solr{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
