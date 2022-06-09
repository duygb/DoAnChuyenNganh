package com.nlu.admin.librarian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibrarianRestController {

    @Autowired
    private LibrarianService userService;

    @PostMapping("/librarians/check_email")
    public String checkDuplicateEmail(@RequestParam(value = "id", required = false) Integer id, @RequestParam("email") String email) {
        return userService.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }
}
