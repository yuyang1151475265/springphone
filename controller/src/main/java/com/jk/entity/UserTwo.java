package com.jk.entity;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class UserTwo implements Serializable {

    @Field
    private String id;
    @Field
    private String name;
    @Field
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date datetime;

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

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "UserTwo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", datetime=" + datetime +
                '}';
    }
}
