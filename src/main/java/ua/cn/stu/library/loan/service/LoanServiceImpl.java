package ua.cn.stu.library.loan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.cn.stu.library.book.dao.BookRepository;
import ua.cn.stu.library.generics.exceptions.NotFoundException;
import ua.cn.stu.library.generics.service.GenericServiceImpl;
import ua.cn.stu.library.loan.dao.LoanRepository;
import ua.cn.stu.library.models.Book;
import ua.cn.stu.library.models.Loan;
import ua.cn.stu.library.models.Reader;
import ua.cn.stu.library.reader.dao.ReaderRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Transactional
class LoanServiceImpl extends GenericServiceImpl<Loan> implements LoanService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final ReaderRepository readerRepository;

    LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository, ReaderRepository readerRepository) {
        super(loanRepository);
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    @Override
    public Loan save(Loan loan) {
        if (loan.getBook() == null || loan.getBook().getId() == null) {
            throw new IllegalArgumentException("Book ID is required");
        }

        Book book = performIssue(loan.getBook().getId());

        loan.setBook(book);
        if (loan.getIssueDate() == null) {
            loan.setIssueDate(LocalDateTime.now());
        }
        loan.setReturnDate(null);

        return super.save(loan);
    }

    @Override
    public Loan update(Integer id, Loan incomingData) {
        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Loan not found with id: " + id));

        handleBookSwap(existingLoan, incomingData.getBook());

        handleReaderChange(existingLoan, incomingData.getReader());

        handleReturnStatusChange(existingLoan, incomingData.getReturnDate());

        if (incomingData.getIssueDate() != null) {
           existingLoan.setIssueDate(incomingData.getIssueDate());
        }

        existingLoan.setReturnDate(incomingData.getReturnDate());

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

    private Book performIssue(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + bookId));
        adjustBookStock(book, -1);
        return book;
    }

    private void handleBookSwap(Loan existingLoan, Book incomingBookData) {
        if (incomingBookData == null || incomingBookData.getId() == null) return;

        Integer oldBookId = existingLoan.getBook().getId();
        Integer newBookId = incomingBookData.getId();

        if (!Objects.equals(oldBookId, newBookId)) {
            if (existingLoan.getReturnDate() == null) {
                adjustBookStock(existingLoan.getBook(), 1);
                Book newBook = performIssue(newBookId);
                existingLoan.setBook(newBook);
            } else {
                Book newBook = bookRepository.findById(newBookId)
                        .orElseThrow(() -> new NotFoundException("Book not found"));
                existingLoan.setBook(newBook);
            }
        }
    }

    private void handleReturnStatusChange(Loan existingLoan, LocalDateTime newReturnDate) {
        boolean wasReturned = existingLoan.getReturnDate() != null;
        boolean willBeReturned = newReturnDate != null;

        if (!wasReturned && willBeReturned) {
            adjustBookStock(existingLoan.getBook(), 1);
        } else if (wasReturned && !willBeReturned) {
            adjustBookStock(existingLoan.getBook(), -1);
        }
    }

    private void handleReaderChange(Loan existingLoan, Reader incomingReaderData) {
        if (incomingReaderData != null && incomingReaderData.getId() != null) {
            if (!Objects.equals(existingLoan.getReader().getId(), incomingReaderData.getId())) {
                Reader newReader = readerRepository.findById(incomingReaderData.getId())
                        .orElseThrow(() -> new NotFoundException("Reader not found"));
                existingLoan.setReader(newReader);
            }
        }
    }

    private void adjustBookStock(Book book, int delta) {
        int newCount = book.getCopies() + delta;

        if (newCount < 0) {
            throw new IllegalStateException("Операція неможлива: немає вільних примірників книги '" + book.getTitle() + "'");
        }

        book.setCopies(newCount);
        bookRepository.save(book);
    }
}
