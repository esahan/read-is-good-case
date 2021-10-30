package com.ege.readingisgood.repositories.book;

import com.ege.readingisgood.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, Long> , BookRepositoryCustom{

    List<Book> findBookByIdIn(List<Long> ids);

}
