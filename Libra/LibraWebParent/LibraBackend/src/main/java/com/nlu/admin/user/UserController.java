package com.nlu.admin.user;

import com.nlu.common.entity.Role;
import com.nlu.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/users")
  public String listAll(Model model) {
    List<User> listUsers = userService.listAll();

    model.addAttribute("listUsers", listUsers);

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
