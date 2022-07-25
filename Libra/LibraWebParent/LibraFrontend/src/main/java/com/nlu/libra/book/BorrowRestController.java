package com.nlu.libra.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowRestController {

    @Autowired
    private BookService service;

    @PostMapping("/return/check_isbn")
    public String checkIsbnExist(@RequestParam(value = "id", required = false) Integer id,
                                     @RequestParam("isbn") String isbn) {
        return service.isIsbnUnique(id, isbn) ? "OK" : "Duplicated";
    }
}
