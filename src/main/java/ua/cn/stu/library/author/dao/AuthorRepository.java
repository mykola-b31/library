package ua.cn.stu.library.author.dao;

import ua.cn.stu.library.generics.dao.GenericRepository;
import ua.cn.stu.library.models.Author;

import java.util.List;

public interface AuthorRepository extends GenericRepository<Author> {

    List<Author> findByNameContainingIgnoreCase(String name);
}
