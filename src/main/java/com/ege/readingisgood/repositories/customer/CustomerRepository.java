package com.ege.readingisgood.repositories.customer;

import com.ege.readingisgood.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository  extends MongoRepository<Customer, Long> , CustomerRepositoryCustom {


}
