package ua.cn.stu.library.book.service;

import ua.cn.stu.library.generics.service.GenericService;
import ua.cn.stu.library.models.Book;

import java.util.List;

public interface BookService extends GenericService<Book> {

    List<Book> search(String query);
    void deleteBookWithLoans(Integer id, boolean force);
}
