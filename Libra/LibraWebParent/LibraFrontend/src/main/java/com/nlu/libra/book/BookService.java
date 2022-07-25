package com.nlu.libra.book;

import com.nlu.common.entity.Book;
import com.nlu.common.entity.Borrow;
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

    @Autowired
    private BorrowRepository borrowRepo;

    public Page<Book> search(int pageNum, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, BOOK_PER_PAGE);

        if (keyword != null)
            return bookRepo.findAll(keyword, pageable);

        return bookRepo.findAll("", pageable);
    }

    public Book get(Integer id) throws BookNotFoundException {
        try {
            return bookRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new BookNotFoundException("Could not find any Book with ID " + id);
        }
    }

    public Borrow saveBorrow(Borrow borrow) {
        return borrowRepo.save(borrow);
    }

    public Book saveBook(Book book) {
        return bookRepo.save(book);
    }

    public void delete(Integer id) throws BookNotFoundException {
        Long countById = borrowRepo.countById(id);

        if (countById == null || countById == 0) {
            throw new BookNotFoundException("Could not find any Book with ID" + id);
        }

        borrowRepo.deleteById(id);
    }

    public boolean isIsbnUnique(Integer id, String isbn) {
        Borrow bookByIsbn = borrowRepo.getBorrow(isbn, id);

        if (bookByIsbn == null) return false;

        return true;
    }

    public Borrow getBorrow(Integer id, String isbn) {
        return borrowRepo.getBorrow(isbn, id);
    }
}
