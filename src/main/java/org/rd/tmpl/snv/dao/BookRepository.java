package org.rd.tmpl.snv.dao;

import java.util.List;

import org.rd.tmpl.snv.dto.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContaining(String title);
}