package com.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="test")
public class Test implements Serializable {
    @Id
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;

    public Test(){
        super();
    }
    public Test(Integer id, String name){
        super();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getU_name() {
        return name;
    }

    public void setU_name(String u_name) {
        this.name = u_name;
    }

    public String toString(){
        return "ID: " + this.id + "\nName: " + this.name;
    }
}
