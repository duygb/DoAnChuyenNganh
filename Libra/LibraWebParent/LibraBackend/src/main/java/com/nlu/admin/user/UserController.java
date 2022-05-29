package com.nlu.admin.user;

import com.nlu.common.entity.Role;
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
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/users") // http://localhost:8080/MTShopAdmin/users
  public String listFirstPage(Model model) {
    return listByPage(1, model, null);
  }

  @GetMapping("/users/page/{pageNumber}")
  public String listByPage(@PathVariable(name = "pageNumber") int pageNum, Model model,
                           @RequestParam(value = "keyword", required = false) String keyword) {
    Page<User> page = userService.listByPage(pageNum, keyword);
    List<User> listUsers = page.getContent();

    long startElementOfPage = (pageNum - 1) * UserService.USER_PER_PAGE + 1;
    long endElementOfPage = startElementOfPage + UserService.USER_PER_PAGE - 1;

    if (endElementOfPage > page.getTotalElements()) {
      endElementOfPage = page.getTotalElements();
    }

    model.addAttribute("currentPage", pageNum);
    model.addAttribute("totalPages", page.getTotalPages());
    model.addAttribute("startCount", startElementOfPage);
    model.addAttribute("endCount", endElementOfPage);
    model.addAttribute("totalItems", page.getTotalElements());
    model.addAttribute("listUsers", listUsers);
    model.addAttribute("keyword", keyword);

    return "users";
  }


  @GetMapping("/users/new")
  public String newUser(Model model) {
    User user = new User();
    List<Role> listRoles = userService.listRoles();

    user.setEnabled(true);

    model.addAttribute("user", user);
    model.addAttribute("listRoles", listRoles);
    model.addAttribute("pageTitle", "Tạo mới User");

    return "user_form";
  }

  @PostMapping("/users/save")
  public String saveUser(User user, RedirectAttributes redirectAttributes) {
    userService.save(user);

    redirectAttributes.addFlashAttribute("message", "User đã được lưu thành công !");

    return "redirect:/users";
  }

  @GetMapping("/users/{id}/enabled/{status}")
  public String updateUserEnabledStatus(@PathVariable(name = "id") Integer id,
                                        @PathVariable(name = "status") boolean enabled,
                                        RedirectAttributes redirectAttributes) {
    userService.updateUserEnabledStatus(id, enabled);

    String status = enabled ? "kích hoạt" : "vô hiệu hoá";
    String message = "User " + id + " đã được " + status;

    redirectAttributes.addFlashAttribute("message", message);

    return "redirect:/users";
  }

  @GetMapping("/users/delete/{id}")
  public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
    try {
      userService.delete(id);

      redirectAttributes.addFlashAttribute("message", "User " + id + " đã xoá thành công !");
    } catch (UserNotFoundException e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/users";
  }
}
