package com.ege.readingisgood.web.model;

import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    @Null
    private Long id;

    @NotBlank(message = "{validation.book.title.notBlank}")
    @Size(min = 1, max = 100,
            message = "{validation.book.title.size}")
    private String title;

    @NotBlank(message = "{validation.book.author.notBlank}")
    @Size(min = 2, max = 100,
            message = "{validation.book.author.size}")
    private String author;

    @NotBlank(message = "{validation.book.publisher.notBlank}")
    @Size(min = 2, max = 100,
            message = "{validation.book.publisher.size}")
    private String publisher;

    private Integer quantityOnHand;

    @NotNull(message = "{validation.book.price.notNull}")
    @Positive(message = "{validation.book.price.positive}")
    private BigDecimal price;

}
