package ua.cn.stu.library.author.service;

import ua.cn.stu.library.generics.service.GenericService;
import ua.cn.stu.library.models.Author;

import java.util.List;

public interface AuthorService extends GenericService<Author> {

    List<Author> search(String query);
}
