package com.fuzzstudio.restapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Map;

@Entity
public class Product {

    @Id
    public String id;

    @ManyToOne
    public Category category;

    public String name;

    public String desc;

    public Map<String, String> attrs;

    public Product(){}

    public Product(Category category, String name, String desc, Map<String, String> attrs) {
        this.category = category;
        this.name = name;
        this.desc = desc;
        this.attrs = attrs;

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        stringBuilder.append("\"id\": \"");
        stringBuilder.append(id);
        stringBuilder.append("\", ");

        stringBuilder.append("\"category\": ");
        stringBuilder.append(category.toString());
        stringBuilder.append(", ");

        stringBuilder.append("\"name\": \"");
        stringBuilder.append(name);
        stringBuilder.append("\", ");

        stringBuilder.append("\"desc\": \"");
        stringBuilder.append(desc);
        stringBuilder.append("\", ");


        stringBuilder.append("\"atrribute\": ");
        stringBuilder.append(mapToString());


        stringBuilder.append("}");

        return stringBuilder.toString();
    }
    
    private String mapToString() {
        
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append("[");
        for (Map.Entry<String, String> entry : attrs.entrySet()) {
            stringBuilder.append("{");
            stringBuilder.append("\"");
            stringBuilder.append(entry.getKey());
            stringBuilder.append("\": \"");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\"}");
            stringBuilder.append(",");
        }
        if(attrs.entrySet().size() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
