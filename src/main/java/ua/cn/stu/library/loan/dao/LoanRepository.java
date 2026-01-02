package ua.cn.stu.library.loan.dao;

import ua.cn.stu.library.generics.dao.GenericRepository;
import ua.cn.stu.library.models.Loan;

public interface LoanRepository extends GenericRepository<Loan> {
    void deleteByReturnDateIsNotNull();
}
