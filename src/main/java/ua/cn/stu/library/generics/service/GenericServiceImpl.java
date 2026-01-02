package ua.cn.stu.library.generics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.cn.stu.library.generics.dao.GenericRepository;
import ua.cn.stu.library.generics.exceptions.InvalidRequestMethodException;
import ua.cn.stu.library.generics.exceptions.NotFoundException;
import ua.cn.stu.library.models.BaseEntity;

@Service
@Transactional
@RequiredArgsConstructor
public class GenericServiceImpl <T extends BaseEntity> implements GenericService <T> {

    private final GenericRepository <T> genericRepository;

    @Override
    public Iterable<T> findAll() {
        return genericRepository.findAll();
    }

    @Override
    public T findById(Integer id) throws NotFoundException {
        return genericRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public T save(T t) {
        if (t.getId() != null) {
            throw new InvalidRequestMethodException();
        }
        return genericRepository.save(t);
    }

    @Override
    public T update(Integer id, T t) {
        t.setId(id);
        return genericRepository.save(t);
    }

    @Override
    public void deleteById(Integer id) {
        if (!genericRepository.existsById(id)) {
            throw new NotFoundException();
        }
        genericRepository.deleteById(id);
    }
}
