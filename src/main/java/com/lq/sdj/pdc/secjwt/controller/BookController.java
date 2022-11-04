package com.lq.sdj.pdc.secjwt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lq.sdj.pdc.secjwt.dao.BookRepository;
import com.lq.sdj.pdc.secjwt.dto.Book;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    @PreAuthorize("hasRole('ROLE_SELECT')")
    @ApiOperation(value = "Obtenir la liste des livres", response = Book.class)
    @ApiImplicitParam(name = "Authorization", value = "jeton JWT", required = true, 
                      allowEmptyValue = false, paramType = "header", 
                      dataTypeClass = String.class, example = "Bearer jeton-JWT")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String title) {
        try {
            List<Book> books = new ArrayList<Book>();

            if (title == null)
                bookRepository.findAll().forEach(books::add);
            else
                bookRepository.findByTitleContaining(title).forEach(books::add);

            if (books.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/books/{id}")
    @PreAuthorize("hasRole('ROLE_SELECT')")
    @ApiOperation(value = "Obtenir un livre par son identifiant", response = Book.class)
    @ApiImplicitParam(name = "Authorization", value = "jeton JWT", required = true, 
                      allowEmptyValue = false, paramType = "header", 
                      dataTypeClass = String.class, example = "Bearer jeton-JWT")
    public ResponseEntity<Book> getTutorialById(@PathVariable("id") long id) {
        Optional<Book> bookData = bookRepository.findById(id);

        if (bookData.isPresent())
            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/books")
    @PreAuthorize("hasRole('ROLE_INSERT')")
    @ApiOperation(value = "Insérer un livre", response = Book.class)
    @ApiImplicitParam(name = "Authorization", value = "jeton JWT", required = true, 
                      allowEmptyValue = false, paramType = "header", 
                      dataTypeClass = String.class, example = "Bearer jeton-JWT")
    public ResponseEntity<Book> createTutorial(@RequestBody Book newBook) {
        try {
            Book book = bookRepository.save(new Book(newBook.getTitle(), newBook.getDescription()));
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        } catch (Exception e) {
           return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/books/{id}")
    @PreAuthorize("hasRole('ROLE_UPDATE')")
    @ApiOperation(value = "Mettre à jour un livre", response = Book.class)
    @ApiImplicitParam(name = "Authorization", value = "jeton JWT", required = true, 
                      allowEmptyValue = false, paramType = "header", 
                      dataTypeClass = String.class, example = "Bearer jeton-JWT")
    public ResponseEntity<Book> updateTutorial(@PathVariable("id") long id, @RequestBody Book newBook) {
        Optional<Book> tutorialData = bookRepository.findById(id);

        if (tutorialData.isPresent()) {
            Book book = tutorialData.get();
            book.setTitle(newBook.getTitle());
            book.setDescription(newBook.getDescription());
            return new ResponseEntity<>(bookRepository.save(book), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasRole('ROLE_DELETE')")
    @ApiOperation(value = "Supprimer un livre par son identifiant", response = Book.class)
    @ApiImplicitParam(name = "Authorization", value = "jeton JWT", required = true, 
                      allowEmptyValue = false, paramType = "header", 
                      dataTypeClass = String.class, example = "Bearer jeton-JWT")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            bookRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/books")
    @PreAuthorize("hasRole('ROLE_DELETE')")
    @ApiOperation(value = "Supprimer tous les livres", response = Book.class)
    @ApiImplicitParam(name = "Authorization", value = "jeton JWT", required = true, 
                      allowEmptyValue = false, paramType = "header", 
                      dataTypeClass = String.class, example = "Bearer jeton-JWT")
    public ResponseEntity<HttpStatus> deleteAllBooks() {
        try {
            bookRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}