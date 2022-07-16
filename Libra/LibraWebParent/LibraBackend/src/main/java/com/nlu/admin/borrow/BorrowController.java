package com.nlu.admin.borrow;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BorrowController {
  @GetMapping("/borrows")
  public String newUser(Model model) {

    return "borrows/borrows";
  }
}
