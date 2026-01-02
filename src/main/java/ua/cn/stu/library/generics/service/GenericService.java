package ua.cn.stu.library.generics.service;

import ua.cn.stu.library.generics.exceptions.NotFoundException;
import ua.cn.stu.library.models.BaseEntity;

public interface GenericService <T extends BaseEntity> {

    Iterable<T> findAll();

    T findById(Integer id) throws NotFoundException;

    T save(T t);

    T update(Integer id, T t);

    void deleteById (Integer id);

}
