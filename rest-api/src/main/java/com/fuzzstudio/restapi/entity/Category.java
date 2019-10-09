package com.fuzzstudio.restapi.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;

@Entity
public class Category {

    @Id
    public String id;

    public String name;

    public String desc;

    public String image;

    public Category() {}

    public Category(String name, String desc, String image) {
        this.name = name;
        this.desc = desc;
        this.image = image;
    }

    @Override
    public String toString() {
        return String.format(
                "{\"id\": \"%s\", \"name\": \"%s\", \"desc\": \"%s\", \"image\": \"%s\"}",
                id, name, desc, image);
    }
}
