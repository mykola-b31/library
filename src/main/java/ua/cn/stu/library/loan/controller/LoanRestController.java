package ua.cn.stu.library.loan.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.cn.stu.library.generics.controller.GenericRestController;
import ua.cn.stu.library.generics.service.GenericService;
import ua.cn.stu.library.loan.service.LoanService;
import ua.cn.stu.library.models.Loan;

@RestController
@RequestMapping("/api/loans")
@Tag(name = "RESTful Web Services for Loans", description = "Endpoints for managing loans")
public class LoanRestController extends GenericRestController<Loan> {

    private final LoanService loanService;

    public LoanRestController(LoanService loanService) {
        super(loanService);
        this.loanService = loanService;
    }

    @DeleteMapping("/history")
    public void clearHistory() {
        loanService.deleteReturnedLoans();
    }
}
