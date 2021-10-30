package com.ege.readingisgood.config;

import com.mongodb.WriteConcern;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.WriteConcernResolver;

@Configuration
public class MongoConfiguration {
    @Bean
    public WriteConcernResolver writeConcernResolver() {
       return action -> WriteConcern.ACKNOWLEDGED;
    }

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}
