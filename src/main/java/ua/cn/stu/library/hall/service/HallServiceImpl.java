package ua.cn.stu.library.hall.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.cn.stu.library.generics.service.GenericServiceImpl;
import ua.cn.stu.library.hall.dao.HallRepository;
import ua.cn.stu.library.models.Hall;

@Service
public class HallServiceImpl extends GenericServiceImpl<Hall> implements HallService {

    public HallServiceImpl(HallRepository hallRepository) {
        super(hallRepository);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Hall hall = super.findById(id);

        if (hall.getBooks() != null && !hall.getBooks().isEmpty()) {
            throw new IllegalStateException("Не можна видалити зал '" + hall.getName() +
                    "', оскільки в ньому зберігається " + hall.getBooks().size() + " книг. Спочатку перемістіть книги");
        }

        super.deleteById(id);
    }
}
