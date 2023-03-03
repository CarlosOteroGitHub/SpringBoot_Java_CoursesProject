package com.backend.exams.controllers;

import com.backend.exams.auxiliar.ApiExceptionHandler;
import com.backend.exams.auxiliar.Auxiliar;
import com.backend.exams.models.AsignaturaModel;
import com.backend.exams.services.AsignaturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Tag(name = "API Asignatura", description = "API´s del Servicio Asignaturas")
@RestController
public class AsignaturaRestController {
    
    @Autowired
    AsignaturaService asignaturaService;
    
    @Operation(summary = "Retorna el Template HTML del Servicio", description = "API para Retornar el Template del Servicio Asignatura")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/asignatura-template", method = RequestMethod.GET)
    public ModelAndView asignatura(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("asignatura.html");
        return modelAndView;
    }

    @Operation(summary = "Retorna un Listado de Elementos", description = "API para Retornar el Listado de Elementos del Servicio Asignatura")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/listar-asignaturas", method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        List<AsignaturaModel> lista = (List<AsignaturaModel>) asignaturaService.findAll();
        if(lista == null || lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(asignaturaService.findAll());
    }
    
    @Operation(summary = "Retorna un Listado de Elementos con Paginación", description = "API para Retornar el Listado de Elementos con Paginación del Servicio Asignatura")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/asignatura-page", method = RequestMethod.GET)
    public ResponseEntity<?> get_page(Pageable pageable) {
        List<AsignaturaModel> lista = (List<AsignaturaModel>) asignaturaService.findAll();
        if(lista == null || lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(asignaturaService.findAll(pageable));
    }
    
    @Operation(summary = "Retorna el Detalle de un Elemento por ID", description = "API para Retornar el Detalle de un Elemento por ID del Servicio Asignatura")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/detalle-asignatura/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<AsignaturaModel> optional = asignaturaService.findById(id);
        if (optional == null || optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @Operation(summary = "Agrega un Nuevo Elemento", description = "API para Agregar un Nuevo Elemento al Servicio Asignatura")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/agregar-asignatura", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody AsignaturaModel asignaturaModel, BindingResult result) {
        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(asignaturaService.save(asignaturaModel));
    }

    @Operation(summary = "Actualiza un Elemento Existente", description = "API para Actualizar un Elemento Existente en el Servicio Asignatura")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/actualizar-asignatura/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@Valid @RequestBody AsignaturaModel asignaturaModel, BindingResult result, @PathVariable long id) {
        Optional<AsignaturaModel> optional = asignaturaService.findById(id);
        if (optional == null || optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }

        AsignaturaModel asignaturaModel_db = optional.get();
        asignaturaModel_db.setNombre(asignaturaModel.getNombre());

        return ResponseEntity.status(HttpStatus.OK).body(asignaturaService.save(asignaturaModel_db));
    }

    @Operation(summary = "Elimina un Elemento Existente", description = "API para Eliminar un Elemento Existente en el Servicio Asignatura")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/eliminar-asignatura/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<AsignaturaModel> optional;
        try {
            optional = asignaturaService.findById(id);
            if (optional == null || optional.isEmpty());
            asignaturaService.deleteById(id);
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }
}