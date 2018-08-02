package com.example.webfluxdemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by jjcosare on 7/5/18.
 */
@Data
@NoArgsConstructor
@Document
public class User {

    @Id
    private String id;

    @NotBlank
    private String email;

    @NotNull
    private Date createdAt = new Date();

    public User(String email){
        this.email = email;
    }

}
