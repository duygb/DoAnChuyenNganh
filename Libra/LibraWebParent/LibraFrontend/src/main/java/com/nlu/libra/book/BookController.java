package com.nlu.libra.book;

import com.nlu.common.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String listFirstPage(Model model) {
        return listByPage(1, model, null);
    }

    @GetMapping("/books/page/{pageNumber}")
    public String listByPage(@PathVariable(name = "pageNumber") int pageNum, Model model,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        Page<Book> page = bookService.search(pageNum, keyword);
        List<Book> listBooks = page.getContent();

        long startElementOfPage = (pageNum - 1) * bookService.BOOK_PER_PAGE + 1;
        long endElementOfPage = startElementOfPage + bookService.BOOK_PER_PAGE - 1;

        if (endElementOfPage > page.getTotalElements()) {
            endElementOfPage = page.getTotalElements();
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startElementOfPage);
        model.addAttribute("endCount", endElementOfPage);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listBooks", listBooks);
        model.addAttribute("keyword", keyword);

        return "books";
    }
}
