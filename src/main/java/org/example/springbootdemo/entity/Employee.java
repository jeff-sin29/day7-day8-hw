package org.example.springbootdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
    private double salary;
    private String gender;
    private boolean status;

    @Column(name = "company_id")
    private long companyId;

    public Employee() {
    }

    public Employee(long id, String name, int age, double salary, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.gender = gender;
        this.status = true;
    }

    public boolean getStatus() {
        return status;
    }
}
