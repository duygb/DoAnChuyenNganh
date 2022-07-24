package com.nlu.admin.borrow;

import com.nlu.common.entity.Borrow;
import com.nlu.common.exception.BorrowNotFoundException;
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
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @GetMapping("/borrows")
    public String listFirstPage(Model model) {
        return listByPage(1, model, null);
    }

    @GetMapping("/borrows/page/{pageNumber}")
    public String listByPage(@PathVariable(name = "pageNumber") int pageNum, Model model,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        Page<Borrow> page = borrowService.listByPage(pageNum, keyword);
        List<Borrow> listBorrows = page.getContent();

        long startElementOfPage = (pageNum - 1) * borrowService.BORROW_PER_PAGE + 1;
        long endElementOfPage = startElementOfPage + borrowService.BORROW_PER_PAGE - 1;

        if (endElementOfPage > page.getTotalElements()) {
            endElementOfPage = page.getTotalElements();
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startElementOfPage);
        model.addAttribute("endCount", endElementOfPage);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listBorrows", listBorrows);
        model.addAttribute("keyword", keyword);

        return "borrows/borrows";
    }

    @PostMapping("/borrows/save")
    public String saveBorrow(Borrow borrow, RedirectAttributes redirectAttributes) {
        borrowService.save(borrow);

        redirectAttributes.addFlashAttribute("message", "Thông tin mượn sách đã được lưu thành công !");

        return "redirect:/borrows";
    }

    @GetMapping("/borrows/edit/{id}")
    public String editBorrow(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Borrow borrow = borrowService.get(id);

            model.addAttribute("borrow", borrow);
            model.addAttribute("pageTitle", "Sửa thông tin mượn sách");

            return "borrows/borrow_form";
        } catch (BorrowNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/borrows";
        }
    }

    @GetMapping("/borrows/delete/{id}")
    public String deleteBorrow(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            borrowService.delete(id);

            redirectAttributes.addFlashAttribute("message", "Thông tin mượn sách đã được xoá thành công !");
        } catch (BorrowNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/borrows";
    }

    @GetMapping("/borrows/{id}/enabled/{status}")
    public String updateBorrowEnabledStatus(@PathVariable(name = "id") Integer id,
                                            @PathVariable(name = "status") boolean enabled,
                                            RedirectAttributes redirectAttributes) {
        borrowService.updateBorrowEnabledStatus(id, enabled);

        String status = enabled ? "kích hoạt" : "vô hiệu hoá";
        String message = "Thông tin mượn sách có id là " + id + " đã được " + status;

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/borrows";
    }
}
