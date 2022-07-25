package com.nlu.libra.book;

import com.nlu.common.entity.Book;
import com.nlu.common.entity.Borrow;
import com.nlu.common.exception.BookNotFoundException;
import com.nlu.common.exception.UserNotFoundException;
import com.nlu.libra.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

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

    @GetMapping("/borrow/edit/{id}")
    public String viewBorrow(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes,
                             HttpServletRequest request) {
        if (request.getSession().getAttribute("userLogged") == null) {
            return "redirect:/login";
        }
        try {
            Book book = bookService.get(id);
            Borrow borrow = new Borrow();
            borrow.setEnabled(true);

            model.addAttribute("book", book);
            model.addAttribute("borrow", borrow);

            return "borrows";
        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/books";
        }
    }

    @PostMapping("/borrow/save")
    public String saveBorrow(Borrow borrow, @RequestParam(name = "userId") Integer userId,
                             @RequestParam(name = "bookId") Integer bookId, RedirectAttributes redirectAttributes)
            throws BookNotFoundException, UserNotFoundException {

        Borrow savedBorrow = new Borrow();

        savedBorrow.setUser(userService.get(userId));
        savedBorrow.setBook(bookService.get(bookId));
        savedBorrow.setLoanDate(borrow.getLoanDate());
        savedBorrow.setEnabled(borrow.isEnabled());
        savedBorrow.setReturnedDate(borrow.getReturnedDate());

        bookService.saveBorrow(savedBorrow);

        Book book = bookService.get(bookId);
        byte updateAvailable = (byte) (book.getAvailable() - 1);
        book.setAvailable(updateAvailable);

        bookService.saveBook(book);

        redirectAttributes.addFlashAttribute("message", "Mượn sách thành công !");

        return "redirect:/books";
    }

    @GetMapping("/return")
    public String viewReturn(HttpServletRequest request) {
        if (request.getSession().getAttribute("userLogged") == null) {
            return "redirect:/login";
        }
        return "returns";
    }

    @PostMapping("/return/delete")
    public String returnBook(@RequestParam(name = "isbn") String isbn, @RequestParam(name = "id") Integer id,
                             RedirectAttributes redirectAttributes) throws BookNotFoundException {
        try {
            Borrow borrow = bookService.getBorrow(id, isbn);

            Book book = borrow.getBook();
            byte available = (byte) (book.getAvailable() + 1);
            book.setAvailable(available);

            bookService.saveBook(book);
            bookService.delete(borrow.getId());
            redirectAttributes.addFlashAttribute("message", "Sách được trả thành công !");

        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/books";
    }
}
