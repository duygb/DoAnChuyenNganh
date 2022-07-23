package com.nlu.admin.book;

import com.nlu.common.entity.Book;
import com.nlu.common.entity.Category;
import com.nlu.common.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BookService {

    public static final int BOOK_PER_PAGE = 5;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    public List<Category> listCategories() {
        return (List<Category>) categoryRepo.findAll();
    }

    public Page<Book> listByPage(int pageNum, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, BOOK_PER_PAGE);

        if (keyword != null)
            return bookRepo.findAll(keyword, pageable);

        return bookRepo.findAll("",pageable);
    }

    public Book save(Book book) {

        if (book.getAlias() == null || book.getAlias().isEmpty()) {
            String defaultAlias = book.getName().replaceAll(" ", "-");
            book.setAlias(defaultAlias);
        } else {
            book.setAlias(book.getAlias().replaceAll(" ", "-"));
        }

        return bookRepo.save(book);
    }

    public boolean isIsbnUnique(Integer id, String isbn) {
        Book bookByIsbn = bookRepo.findByIsbn(isbn);

        if (bookByIsbn == null) return true;

        boolean isCreatingNew = (id == null);

        if (isCreatingNew) {
            if (bookByIsbn != null) return false;
        } else {
            if (bookByIsbn.getId() != id) return false;
        }

        return true;
    }

    public Book get(Integer id) throws BookNotFoundException {
        try {
            return bookRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new BookNotFoundException("Could not find any Book with ID " + id);
        }
    }

    public void delete(Integer id) throws BookNotFoundException {
        Long countById = bookRepo.countById(id);

        if (countById == null || countById == 0) {
            throw new BookNotFoundException("Could not find any Book with ID" + id);
        }

        bookRepo.deleteById(id);
    }

    public void updateBookEnabledStatus(Integer id, boolean enabled) {
        bookRepo.updateEnabledStatus(id, enabled);
    }
}
