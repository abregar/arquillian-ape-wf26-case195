package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test")
public class Demo {

    public Demo() {
    }

    private long id;
    private String name;

    @Column
    @Id
    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
