package com.nlu.admin.librarian;

import com.nlu.common.entity.User;
import com.nlu.common.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LibrarianController {

    @Autowired
    private LibrarianService librarianService;

    @GetMapping("/librarians")
    public String listFirstPage(Model model) {
        return listByPage(1, model, null);
    }

    @GetMapping("/librarians/page/{pageNumber}")
    public String listByPage(@PathVariable(name = "pageNumber") int pageNum, Model model,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        Page<User> page = librarianService.listByPage(pageNum, keyword);
        List<User> listLibrarians = page.getContent();

        long startElementOfPage = (pageNum - 1) * LibrarianService.USER_PER_PAGE + 1;
        long endElementOfPage = startElementOfPage + LibrarianService.USER_PER_PAGE - 1;

        if (endElementOfPage > page.getTotalElements()) {
            endElementOfPage = page.getTotalElements();
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startElementOfPage);
        model.addAttribute("endCount", endElementOfPage);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listLibrarians", listLibrarians);
        model.addAttribute("keyword", keyword);

        return "librarians/librarians";
    }

    @GetMapping("/librarians/new")
    public String newLibrarian(Model model) {
        User librarian = new User();

        librarian.setRole(librarianService.getRole());
        librarian.setEnabled(true);

        model.addAttribute("librarian", librarian);
        model.addAttribute("pageTitle", "T???o th??? th?? m???i");

        return "librarians/librarian_form";
    }

    @PostMapping("/librarians/save")
    public String saveLibrarian(User user, RedirectAttributes redirectAttributes) {
        librarianService.save(user);

        redirectAttributes.addFlashAttribute("message", "Th??? th?? ???? ???????c l??u th??nh c??ng !");

        String firstPartOfEmail = user.getEmail().split("@")[0];

        return "redirect:/librarians/page/1?keyword=" + firstPartOfEmail;
    }

    @GetMapping("/librarians/edit/{id}")
    public String editLibrarian(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User librarian = librarianService.get(id);
            librarian.setRole(librarianService.getRole());

            model.addAttribute("librarian", librarian);
            model.addAttribute("pageTitle", "S???a th??? th??");

            return "librarians/librarian_form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/librarians";
        }
    }

    @GetMapping("/librarians/delete/{id}")
    public String deleteLibrarian(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            librarianService.delete(id);

            redirectAttributes.addFlashAttribute("message", "Th??? th?? ???? ???????c xo?? th??nh c??ng !");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/librarians";
    }

    @GetMapping("/librarians/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable(name = "id") Integer id,
                                          @PathVariable(name = "status") boolean enabled,
                                          RedirectAttributes redirectAttributes) {
        librarianService.updateUserEnabledStatus(id, enabled);

        String status = enabled ? "k??ch ho???t" : "v?? hi???u ho??";
        String message = "Th??? th?? " + id + " ???? ???????c " + status;

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/librarians";
    }
}
