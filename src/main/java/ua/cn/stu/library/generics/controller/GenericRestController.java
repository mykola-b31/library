package ua.cn.stu.library.generics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.cn.stu.library.generics.Response;
import ua.cn.stu.library.generics.service.GenericService;
import ua.cn.stu.library.models.BaseEntity;

@RestController
public abstract class GenericRestController <T extends BaseEntity> {

    private final GenericService <T> genericService;

    public GenericRestController(GenericService <T> genericService) {
        this.genericService = genericService;
    }

    @GetMapping
    @Operation(summary = "Find all models", description = "Return a list of all models from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "204", description = "No Content"),
    })
    public ResponseEntity <?> findAll() {
        Iterable <T> models = genericService.findAll();
        return models.iterator().hasNext() ? ResponseEntity.ok(models) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find the model by its id", description = "Return a model from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "204", description = "No Content"),
    })
    public T find(@PathVariable Integer id) {
        return genericService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new model", description = "Create a new model by saving the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New model created", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "405", description = "Method not allowed"),
            @ApiResponse(responseCode = "415", description = "Unsupported Media Type")
    })
    public ResponseEntity <Response <T>> save(@RequestBody T entity) {
        T saveModel = genericService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(saveModel, "Model successfully saved"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing model", description = "Updates an existing model by saving the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Model updated", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    public ResponseEntity <Response <T>> update (@PathVariable Integer id, @RequestBody T entity) {
        T updatedModel = genericService.update(id, entity);
        return ResponseEntity.ok(new Response<>(updatedModel, "Model successfully updated"));
    }

    @DeleteMapping(value = "/{id}", produces = "text/plain")
    @Operation(summary = "Delete an existing model", description = "Delete an existing model from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "No Errors"),
            @ApiResponse(responseCode = "404", description = "Model Not Found")
    })
    public void delete(@PathVariable Integer id) {
        genericService.deleteById(id);
    }

}
