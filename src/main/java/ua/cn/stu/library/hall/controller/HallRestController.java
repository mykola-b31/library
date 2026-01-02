package ua.cn.stu.library.hall.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.cn.stu.library.generics.controller.GenericRestController;
import ua.cn.stu.library.hall.service.HallService;
import ua.cn.stu.library.models.Hall;

@RestController
@RequestMapping("/api/halls")
@Tag(name = "RESTful Web Service for Halls", description = "Endpoints for managing halls")
public class HallRestController extends GenericRestController<Hall> {

    public HallRestController(HallService hallService) {
        super(hallService);
    }

}
