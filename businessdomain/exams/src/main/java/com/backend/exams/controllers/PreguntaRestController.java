package com.backend.exams.controllers;

import com.backend.exams.auxiliar.ApiExceptionHandler;
import com.backend.exams.auxiliar.Auxiliar;
import com.backend.exams.models.PreguntaModel;
import com.backend.exams.services.PreguntaService;
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

@Tag(name = "API Pregunta", description = "API´s del Servicio Preguntas")
@RestController
public class PreguntaRestController {
    
    @Autowired
    PreguntaService preguntaService;
    
    @Operation(summary = "Retorna el Template HTML del Servicio", description = "API para Retornar el Template del Servicio Pregunta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/pregunta-template", method = RequestMethod.GET)
    public ModelAndView pregunta(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pregunta.html");
        return modelAndView;
    }

    @Operation(summary = "Retorna un Listado de Elementos", description = "API para Retornar el Listado de Elementos del Servicio Pregunta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/listar-preguntas", method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        List<PreguntaModel> lista = (List<PreguntaModel>) preguntaService.findAll();
        if(lista == null || lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(preguntaService.findAll());
    }
    
    @Operation(summary = "Retorna un Listado de Elementos con Paginación", description = "API para Retornar el Listado de Elementos con Paginación del Servicio Pregunta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/pregunta-page", method = RequestMethod.GET)
    public ResponseEntity<?> get_page(Pageable pageable) {
        List<PreguntaModel> lista = (List<PreguntaModel>) preguntaService.findAll();
        if(lista == null || lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(preguntaService.findAll(pageable));
    }
    
    @Operation(summary = "Retorna el Detalle de un Elemento por ID", description = "API para Retornar el Detalle de un Elemento por ID del Servicio Pregunta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/detalle-pregunta/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<PreguntaModel> optional = preguntaService.findById(id);
        if (optional == null || optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @Operation(summary = "Agrega un Nuevo Elemento", description = "API para Agregar un Nuevo Elemento al Servicio Pregunta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/agregar-pregunta", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody PreguntaModel preguntaModel, BindingResult result) {
        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(preguntaService.save(preguntaModel));
    }

    @Operation(summary = "Actualiza un Elemento Existente", description = "API para Actualizar un Elemento Existente en el Servicio Pregunta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/actualizar-pregunta/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@Valid @RequestBody PreguntaModel preguntaModel, BindingResult result, @PathVariable long id) {
        Optional<PreguntaModel> optional = preguntaService.findById(id);
        if (optional == null || optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }

        PreguntaModel preguntaModel_db = optional.get();
        preguntaModel_db.setTexto(preguntaModel.getTexto());

        return ResponseEntity.status(HttpStatus.OK).body(preguntaService.save(preguntaModel_db));
    }

    @Operation(summary = "Elimina un Elemento Existente", description = "API para Eliminar un Elemento Existente en el Servicio Pregunta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/eliminar-pregunta/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<PreguntaModel> optional;
        try {
            optional = preguntaService.findById(id);
            if (optional == null || optional.isEmpty());
            preguntaService.deleteById(id);
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }
}