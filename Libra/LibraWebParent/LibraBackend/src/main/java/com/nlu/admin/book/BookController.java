package com.nlu.admin.book;

import com.nlu.admin.FileUploadUtil;
import com.nlu.common.entity.Book;
import com.nlu.common.entity.Category;
import com.nlu.common.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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
        Page<Book> page = bookService.listByPage(pageNum, keyword);
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

        return "books/books";
    }

    @GetMapping("/books/new")
    public String newBook(Model model) {
        Book book = new Book();
        List<Category> listCategories = bookService.listCategories();

        book.setEnabled(true);

        model.addAttribute("book", book);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("pageTitle", "Tạo sách mới");

        return "books/book_form";
    }

    @PostMapping("/books/save")
    public String saveBook(Book book, RedirectAttributes redirectAttributes,
                           @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            book.setPhotos(fileName);

            Book savedBook = bookService.save(book);

            String uploadDir = "images/book-photos/" + savedBook.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (book.getPhotos().isEmpty())
                book.setPhotos(null);
            bookService.save(book);
        }

        redirectAttributes.addFlashAttribute("message", "Sách đã được lưu thành công !");

        return "redirect:/books/page/1?keyword=" + book.getIsbn();
    }

    @GetMapping("/books/edit/{id}")
    public String editBook(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Book book = bookService.get(id);
            List<Category> listCategories = bookService.listCategories();

            model.addAttribute("book", book);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Sửa sách (ID: " + id + ")");

            return "books/book_form";
        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/books";
        }
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            bookService.delete(id);

            redirectAttributes.addFlashAttribute("message", "book " + id + " xoá thành công !");
        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/books";
    }

    @GetMapping("/books/{id}/enabled/{status}")
    public String updateBookEnabledStatus(@PathVariable(name = "id") Integer id,
                                          @PathVariable(name = "status") boolean enabled,
                                          RedirectAttributes redirectAttributes) {
        bookService.updateBookEnabledStatus(id, enabled);

        String status = enabled ? "kích hoạt" : "vô hiệu hoá";
        String message = "Sách có id là " + id + " đã được " + status;

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/books";
    }
}
