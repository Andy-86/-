package com.andy.girl.mapper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Department implements Serializable{
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private Integer did;

    @Column(nullable = false, unique = true)
    private String departname;


    public Department() {
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    @Override
    public String toString() {
        return "Department{" +
                "did=" + did +
                ", departname='" + departname + '\'' +
                '}';
    }
}
