package com.lq.sdj.pdc.secjwt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lq.sdj.pdc.secjwt.dto.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContaining(String title);
}