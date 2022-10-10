package com.mino.springlab.scenario8;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private String employeeId;

    private String name;

    private LocalDate birthDate = LocalDate.now();

    public Employee(String employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
    }
}
