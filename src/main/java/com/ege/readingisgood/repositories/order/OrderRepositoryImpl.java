package com.ege.readingisgood.repositories.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements  OrderRepositoryCustom{

    private final MongoTemplate mongoTemplate;
}
