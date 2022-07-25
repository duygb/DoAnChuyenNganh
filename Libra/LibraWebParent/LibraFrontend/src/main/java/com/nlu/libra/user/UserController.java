package com.nlu.libra.user;

import com.nlu.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/login")
  public String loginPage() {
    return "signin";
  }

  @GetMapping("/register")
  public String registerPage() {
    return "register";
  }

  @PostMapping("/login")
  public String login(User user, RedirectAttributes redirectAttributes, HttpServletRequest request) {
    User userLogged = userService.login(user);
    if (Objects.nonNull(userLogged)){
      if (userLogged.getRole().getId() == 2){
        request.getSession().setAttribute("userLogged", userLogged);
        return "redirect:/";
      }
      redirectAttributes.addFlashAttribute("accessDenied", user);
      return "redirect:/login";
    }
    redirectAttributes.addFlashAttribute("user", user);
    return "redirect:/login";
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request) {
    request.getSession().removeAttribute("userLogged");
    return "redirect:/";
  }

  @PostMapping("/register")
  public String register(User user, RedirectAttributes redirectAttributes, HttpServletRequest request) {
    User userAfterCreated = userService.create(user);
    if (Objects.nonNull(userAfterCreated)){
      request.getSession().setAttribute("user", userAfterCreated);
      return "redirect:/";
    }
    redirectAttributes.addFlashAttribute("user", user);
    return "redirect:/register";
  }

}
