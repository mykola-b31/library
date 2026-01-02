package ua.cn.stu.library.book.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.cn.stu.library.book.dao.BookRepository;
import ua.cn.stu.library.generics.service.GenericServiceImpl;
import ua.cn.stu.library.models.Book;
import ua.cn.stu.library.models.Loan;

import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl extends GenericServiceImpl<Book> implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        super(bookRepository);
        this.bookRepository = bookRepository;
    }

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAllByOrderByHallNameAscTitleAsc();
    }

    @Override
    public List<Book> search(String query) {
        return bookRepository.findByTitleContainingIgnoreCase(query);
    }

    @Override
    @Transactional
    public void deleteBookWithLoans(Integer id, boolean force) {
        Book book = super.findById(id);
        Set<Loan> loans = book.getLoans();

        if (loans != null && !loans.isEmpty()) {
            boolean hasActiveLoans = loans.stream()
                    .anyMatch(loan -> loan.getReturnDate() == null);

            if (hasActiveLoans) {
                throw new IllegalStateException("ACTIVE_LOANS_EXIST");
            }

            if (!force) {
                throw new IllegalStateException("HISTORY_EXISTS");
            }
        }

        super.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Book book = super.findById(id);

        if (book.getLoans() != null && !book.getLoans().isEmpty()) {
            throw new IllegalStateException("Не можна видалити книгу '" + book.getTitle() + "', оскільки вона на руках у читача.");
        }

        super.deleteById(id);
    }
}
