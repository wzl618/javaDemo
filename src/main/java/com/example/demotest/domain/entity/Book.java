package com.example.demotest.domain.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
@Data
public class Book {

    public Long id;
    @NotEmpty
    public String name;
    @NotEmpty
    public String description;

    public Book(long id,String name, String description)
    {
        this.id=id;
        this.name=name;
        this.description=description;
    }
}
