package com.ege.readingisgood.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Document(collection = "customer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Transient
    public static final String SEQUENCE_NAME = "customer_sequence";

    @Id
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Indexed(unique = true)
    private String email;

    @NotBlank
    private String gender;


}
