package com.ege.readingisgood.repositories.user;

import com.ege.readingisgood.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> , UserRepositoryCustom {

    Optional<User> findByEmail (String email);

    boolean existsByEmail(String email);


}
