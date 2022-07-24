package com.nlu.admin.borrow;

import com.nlu.common.entity.Borrow;
import com.nlu.common.exception.BorrowNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class BorrowService {

    public static final int BORROW_PER_PAGE = 5;

    @Autowired
    private BorrowRepository borrowRepo;

    public Page<Borrow> listByPage(int pageNum, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, BORROW_PER_PAGE);

        if (keyword != null)
            return borrowRepo.findAll(keyword, pageable);

        return borrowRepo.findAll("", pageable);
    }

    public Borrow save(Borrow borrow) {

        return borrowRepo.save(borrow);
    }

    public Borrow get(Integer id) throws BorrowNotFoundException {
        try {
            return borrowRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new BorrowNotFoundException("Could not find any user with ID" + id);
        }
    }

    public void delete(Integer id) throws BorrowNotFoundException {
        Long countById = borrowRepo.countById(id);

        if (countById == null || countById == 0) {
            throw new BorrowNotFoundException("Could not find any borrow with ID " + id);
        }

        borrowRepo.deleteById(id);
    }

    public void updateBorrowEnabledStatus(Integer id, boolean enabled) {
        borrowRepo.updateEnabledStatus(id, enabled);
    }
}
