package ua.cn.stu.library.loan.service;

import ua.cn.stu.library.generics.service.GenericService;
import ua.cn.stu.library.models.Loan;

public interface LoanService extends GenericService<Loan> {
    void deleteReturnedLoans();
}
