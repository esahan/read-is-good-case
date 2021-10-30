package com.ege.readingisgood.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class UserStatisticsSummary {

    private Integer totalOrderCount;
    private Integer totalBookCount;
    private BigDecimal totalPurchasedAmount;

}
