package ua.cn.stu.library.reader.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.cn.stu.library.generics.controller.GenericRestController;
import ua.cn.stu.library.models.Reader;
import ua.cn.stu.library.reader.service.ReaderService;

import java.util.List;

@RestController
@RequestMapping("/api/readers")
@Tag(name = "RESTful Web Services for Readers", description = "Endpoints for managing readers")
public class ReaderRestController extends GenericRestController<Reader> {

    private final ReaderService readerService;

    public ReaderRestController(ReaderService readerService) {
        super(readerService);
        this.readerService = readerService;
    }

    @GetMapping("/search")
    public List<Reader> search(@RequestParam String query) {
        return readerService.search(query);
    }
}
