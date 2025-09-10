package org.example.springbootdemo.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
    private long id;
    private String name;
    private int age;
    private double salary;
    private String gender;
    private boolean status;

    public Employee(long id, String name, int age, double salary, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.gender = gender;
        this.status = true;
    }

}
