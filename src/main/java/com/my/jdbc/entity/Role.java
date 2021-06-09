package com.my.jdbc.entity;

import java.io.Serializable;

public class Role implements Serializable {
    private static final long serialVersionUID = -6137232261065720446L;
    private long id;
    private String name;

    static public Role createRole(String name) {
        Role role = new Role();
        role.setName(name);
        return role;
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
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
