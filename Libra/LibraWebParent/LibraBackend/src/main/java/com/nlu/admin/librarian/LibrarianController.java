package com.nlu.admin.librarian;

import com.nlu.admin.utils.UserNotFoundException;
import com.nlu.common.entity.User;
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

    return "librarians";
  }

  @GetMapping("/librarians/new")
  public String newUser(Model model) {
    User librarian = new User();

    librarian.setRole(librarianService.getRole());
    librarian.setEnabled(true);

    model.addAttribute("librarian", librarian);
    model.addAttribute("pageTitle", "Tạo người dùng mới");

    return "librarian_form";
  }

  @PostMapping("/librarians/save")
  public String saveUser(User user, RedirectAttributes redirectAttributes) {
    librarianService.save(user);

    redirectAttributes.addFlashAttribute("message", "User đã được lưu thành công !");

    String firstPartOfEmail = user.getEmail().split("@")[0];

    return "redirect:/librarians/page/1?keyword=" + firstPartOfEmail;
  }

  @GetMapping("/librarians/edit/{id}")
  public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      User librarian = librarianService.get(id);
      librarian.setRole(librarianService.getRole());

      model.addAttribute("librarian", librarian);
      model.addAttribute("pageTitle", "Sửa người dùng (ID: " + id + ")");

      return "librarian_form";
    } catch (UserNotFoundException e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());

      return "redirect:/librarians";
    }
  }

  @GetMapping("/librarians/delete/{id}")
  public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
    try {
      librarianService.delete(id);

      redirectAttributes.addFlashAttribute("message", "User " + id + " xoá thành công !");
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

    String status = enabled ? "kích hoạt" : "vô hiệu hoá";
    String message = "User " + id + " đã được " + status;

    redirectAttributes.addFlashAttribute("message", message);

    return "redirect:/librarians";
  }
}
