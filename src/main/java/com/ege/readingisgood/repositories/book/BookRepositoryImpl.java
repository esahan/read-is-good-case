package com.ege.readingisgood.repositories.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepositoryCustom{

    private final MongoTemplate mongoTemplate;


}
