package com.nlu.libra.book;

import com.nlu.common.entity.Book;
import com.nlu.common.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BookService {

    public static final int BOOK_PER_PAGE = 9;

    @Autowired
    private BookRepository bookRepo;

    public Page<Book> search(int pageNum, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, BOOK_PER_PAGE);

        if (keyword != null)
            return bookRepo.findAll(keyword, pageable);

        return bookRepo.findAll("",pageable);
    }

    public Book get(Integer id) throws BookNotFoundException {
        try {
            return bookRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new BookNotFoundException("Could not find any Book with ID " + id);
        }
    }
}
