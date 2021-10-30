package com.ege.readingisgood.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document(collection = "order")
public class Order {

    @Transient
    public static final String SEQUENCE_NAME = "order_sequence";

    @Id
    private Long id;

    @NotNull
    private Long customerId;

    @NotEmpty
    private Map<Long,Integer> bookOrderIdQuantityMap;

    private BigDecimal totalPurchaseAmount;

    private LocalDateTime purchaseDate;
}
