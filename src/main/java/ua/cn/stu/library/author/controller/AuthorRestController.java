package ua.cn.stu.library.author.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.cn.stu.library.author.service.AuthorService;
import ua.cn.stu.library.generics.controller.GenericRestController;
import ua.cn.stu.library.models.Author;

import java.util.List;

@RestController
@RequestMapping(value = "/api/authors")
@Tag(name = "RESTful Web Service for Authors", description = "Endpoints for managing authors")
public class AuthorRestController extends GenericRestController<Author> {

    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        super(authorService);
        this.authorService = authorService;
    }

    @GetMapping("/search")
    public List<Author> search(@RequestParam String query) {
        return authorService.search(query);
    }
}
