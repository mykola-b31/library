package ua.cn.stu.library.author.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.cn.stu.library.author.dao.AuthorRepository;
import ua.cn.stu.library.generics.service.GenericServiceImpl;
import ua.cn.stu.library.models.Author;

import java.util.List;

@Service
public class AuthorServiceImpl extends GenericServiceImpl<Author> implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        super(authorRepository);
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> search(String query) {
        return authorRepository.findByNameContainingIgnoreCase(query);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Author author = super.findById(id);

        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            throw new IllegalStateException("Не можна видалити автора '" + author.getName() +
                    "', оскільки у бібліотеці зареєстровано " + author.getBooks().size() + " його книг. Спочатку видаліть книги.");
        }

        super.deleteById(id);
    }
}
