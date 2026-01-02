package ua.cn.stu.library.loan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.cn.stu.library.book.dao.BookRepository;
import ua.cn.stu.library.generics.exceptions.NotFoundException;
import ua.cn.stu.library.generics.service.GenericServiceImpl;
import ua.cn.stu.library.loan.dao.LoanRepository;
import ua.cn.stu.library.models.Book;
import ua.cn.stu.library.models.Loan;

import java.time.LocalDateTime;

@Service
@Transactional
class LoanServiceImpl extends GenericServiceImpl<Loan> implements LoanService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository) {
        super(loanRepository);
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Loan save(Loan loan) {
        if (loan.getBook() == null || loan.getBook().getId() == null) {
            throw new IllegalArgumentException("Book ID is required");
        }
        Integer bookId = loan.getBook().getId();

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + bookId));

        if (book.getCopies() <= 0) {
            throw new IllegalStateException("Всі примірники книги '" + book.getTitle() + "' вже видані!");
        }

        book.setCopies(book.getCopies() - 1);
        bookRepository.save(book);

        if (loan.getIssueDate() == null) {
            loan.setIssueDate(LocalDateTime.now());
        }

        loan.setReturnDate(null);

        loan.setBook(book);

        return super.save(loan);
    }

    @Override
    public Loan update(Integer id, Loan updateLoanData) {
        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Loan not found with id: " + id));

        boolean isReturning = existingLoan.getReturnDate() == null && updateLoanData.getReturnDate() != null;

        if (isReturning) {
            Book book = existingLoan.getBook();

            book.setCopies(book.getCopies() + 1);
            bookRepository.save(book);

            existingLoan.setReturnDate(updateLoanData.getReturnDate());
        } else if (existingLoan.getReturnDate() != null && updateLoanData.getReturnDate() != null) {
            existingLoan.setReturnDate(updateLoanData.getReturnDate());
        }

        return loanRepository.save(existingLoan);
    }

    @Override
    public void deleteById(Integer id) {
        Loan loan = super.findById(id);

        if (loan.getReturnDate() == null) {
            throw new IllegalStateException("Не можна видалити активну позику. Спочатку поверніть книгу.");
        }

        super.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteReturnedLoans() {
        loanRepository.deleteByReturnDateIsNotNull();
    }
}
