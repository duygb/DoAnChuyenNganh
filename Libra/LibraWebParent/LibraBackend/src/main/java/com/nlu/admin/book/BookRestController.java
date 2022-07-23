package com.nlu.admin.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookRestController {

    @Autowired
    private BookService service;

    @PostMapping("/books/check_isbn")
    public String checkDuplicateIsbn(@RequestParam(value = "id", required = false) Integer id,
                                     @RequestParam("isbn") String isbn) {
        return service.isIsbnUnique(id, isbn) ? "OK" : "Duplicated";
    }
}
