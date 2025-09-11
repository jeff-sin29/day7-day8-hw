package org.example.springbootdemo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmployeeReq {
    private String name;

    private int age;

    private int salary;

    public UpdateEmployeeReq(String name, int age, int salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

}
