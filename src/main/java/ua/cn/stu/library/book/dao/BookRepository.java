package ua.cn.stu.library.book.dao;

import ua.cn.stu.library.generics.dao.GenericRepository;
import ua.cn.stu.library.models.Book;

import java.util.List;

public interface BookRepository extends GenericRepository<Book> {

    List<Book> findAllByOrderByHallNameAscTitleAsc();

    List<Book> findByTitleContainingIgnoreCase(String title);
}
