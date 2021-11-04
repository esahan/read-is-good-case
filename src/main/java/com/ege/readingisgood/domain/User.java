package com.ege.readingisgood.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Document(collection = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";
    @Id
    private long id;

    @NotBlank
    private String type;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String password;

    @NotBlank
    @Indexed(unique = true)
    private String email;

    @NotBlank
    private String gender;

    private List<String> roles;

}
