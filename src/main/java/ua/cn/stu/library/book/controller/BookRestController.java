package ua.cn.stu.library.book.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.cn.stu.library.book.service.BookService;
import ua.cn.stu.library.generics.controller.GenericRestController;
import ua.cn.stu.library.models.Book;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/books")
@Tag(name = "RESTful Web Services for Books", description = "Endpoints for managing books")
public class BookRestController extends GenericRestController<Book> {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        super(bookService);
        this.bookService = bookService;
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam String query) {
        return bookService.search(query);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        bookService.deleteBookWithLoans(id, false);
    }

    @DeleteMapping("/{id}/withLoans")
    public ResponseEntity<?> deleteWithLoans(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "false") boolean force
    ) {
        try {
            bookService.deleteBookWithLoans(id, force);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            if ("ACTIVE_LOANS_EXIST".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("code", "ACTIVE_LOANS", "message", "Книга на руках у читачів. Видалення неможливе."));
            }
            if ("HISTORY_EXISTS".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("code", "CONFIRM_REQUIRED", "message", "У книги є історія позик. Видалити книгу разом з історією?"));
            }
            throw e;
        }
    }
}
