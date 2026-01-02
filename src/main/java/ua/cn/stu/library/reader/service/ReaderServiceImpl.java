package ua.cn.stu.library.reader.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.cn.stu.library.generics.service.GenericServiceImpl;
import ua.cn.stu.library.models.Reader;
import ua.cn.stu.library.reader.dao.ReaderRepository;

import java.util.List;

@Service
public class ReaderServiceImpl extends GenericServiceImpl<Reader> implements ReaderService {

    private final ReaderRepository readerRepository;

    public ReaderServiceImpl(ReaderRepository readerRepository) {
        super(readerRepository);
        this.readerRepository = readerRepository;
    }

    @Override
    public List<Reader> search(String query) {
        return readerRepository.findByNameContainingIgnoreCaseOrPhoneContaining(query, query);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Reader reader = super.findById(id);

        if (reader.getLoans() != null) {
            boolean hasActiveLoans = reader.getLoans().stream()
                    .anyMatch(loan -> loan.getReturnDate() == null);

            if (hasActiveLoans) {
                throw new IllegalStateException("Не можна видалити читача '" + reader.getName() +
                        "', оскільки у нього на руках є книги. Спочатку поверніть книги.");
            }
        }

        super.deleteById(id);
    }
}
