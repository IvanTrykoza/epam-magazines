package com.my.jdbc.entity;

import java.io.Serializable;

public class Category implements Serializable {
    private static final long serialVersionUID = -7253339436449210973L;

    private long id;
    private String name;

    static public Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
