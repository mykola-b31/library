package ua.cn.stu.library.reader.dao;

import ua.cn.stu.library.generics.dao.GenericRepository;
import ua.cn.stu.library.models.Reader;

import java.util.List;

public interface ReaderRepository extends GenericRepository<Reader> {

    List<Reader> findByNameContainingIgnoreCaseOrPhoneContaining(String name, String phone);
}
