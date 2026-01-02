package ua.cn.stu.library.librarian.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.cn.stu.library.generics.controller.GenericRestController;
import ua.cn.stu.library.librarian.service.LibrarianService;
import ua.cn.stu.library.models.librarian.Librarian;

@RestController
@RequestMapping("/api/librarians")
@Tag(name = "RESTful Web Service for Librarians", description = "Endpoints for managing librarians")
public class LibrarianRestController extends GenericRestController<Librarian> {

    public LibrarianRestController(LibrarianService librarianService) {
        super(librarianService);
    }
}
