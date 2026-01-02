package ua.cn.stu.library.reader.service;

import ua.cn.stu.library.generics.service.GenericService;
import ua.cn.stu.library.models.Reader;

import java.util.List;

public interface ReaderService extends GenericService<Reader> {

    List<Reader> search(String query);
}
