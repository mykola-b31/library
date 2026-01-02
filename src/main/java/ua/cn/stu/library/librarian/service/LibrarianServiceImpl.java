package ua.cn.stu.library.librarian.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.cn.stu.library.generics.service.GenericServiceImpl;
import ua.cn.stu.library.librarian.dao.LibrarianRepository;
import ua.cn.stu.library.models.librarian.Librarian;

@Service
public class LibrarianServiceImpl extends GenericServiceImpl<Librarian> implements LibrarianService {

    public LibrarianServiceImpl(LibrarianRepository librarianRepository) {
        super(librarianRepository);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Librarian librarian = super.findById(id);

        if (librarian.getHalls() != null && !librarian.getHalls().isEmpty()) {
            throw new IllegalStateException("Не можна звільнити співробітника '" + librarian.getName() +
                    "', оскільки він призначений відповідальним за " + librarian.getHalls().size() + " зал(ів). Спочатку призначте іншого бібліотекаря для цих залів.");
        }

        super.deleteById(id);
    }
}
