package com.ege.readingisgood.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Document(collection = "book")
@Builder
@AllArgsConstructor
@CompoundIndex(def = "{'title': 1, 'author': 1}", unique = true)
public class Book {

    @Transient
    public static final String SEQUENCE_NAME = "book_sequence";

    @Id
    private long id;

    @Version
    private Long version;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String publisher;

    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;

}
