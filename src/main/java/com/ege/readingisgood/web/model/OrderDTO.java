package com.ege.readingisgood.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    @Null
    private Long id;

    @Null
    //@NotNull(message = "{validation.order.customerId.notNull}")
    private Long customerId;

    @NotEmpty(message = "{validation.order.bookOrder.notEmpty}")
    private Map<Long,Integer> bookOrderIdQuantityMap;

    @Null
    private BigDecimal totalPurchaseAmount;

    private LocalDateTime purchaseDate;

}
