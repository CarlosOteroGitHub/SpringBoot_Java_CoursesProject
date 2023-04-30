package com.backend.answers.controllers;

import com.backend.answers.auxiliar.ApiExceptionHandler;
import com.backend.answers.auxiliar.Auxiliar;
import com.backend.answers.models.RespuestaModel;
import com.backend.answers.services.RespuestaService;
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

@Tag(name = "API Respuesta", description = "API´s del Servicio Respuesta")
@RestController
public class RespuestaRestController {
    
    @Autowired
    RespuestaService respuestaService;
    
    @Operation(summary = "Retorna el Template HTML del Servicio", description = "API para Retornar el Template del Servicio Respuesta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/respuesta-template", method = RequestMethod.GET)
    public ModelAndView respuesta(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("respuesta.html");
        return modelAndView;
    }

    @Operation(summary = "Retorna un Listado de Elementos con Paginación", description = "API para Retornar el Listado de Elementos con Paginación del Servicio Respuesta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/listar-respuestas", method = RequestMethod.GET)
    public ResponseEntity<?> get(Pageable pageable) {
        List<RespuestaModel> lista;
        try {
            lista = (List<RespuestaModel>) respuestaService.findAll();
            if(lista == null || lista.isEmpty());
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(respuestaService.findAll(pageable));
    }

    @Operation(summary = "Retorna el Detalle de un Elemento por ID", description = "API para Retornar el Detalle de un Elemento por ID del Servicio Respuesta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/detalle-respuesta/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<RespuestaModel> optional;
        try {
            optional = respuestaService.findById(id);
            if (optional == null || optional.isEmpty());
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }

    @Operation(summary = "Agrega un Nuevo Elemento", description = "API para Agregar un Nuevo Elemento al Servicio Respuesta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/agregar-respuesta", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody RespuestaModel respuestaModel, BindingResult result) {
        if(result.hasErrors()){
            return new Auxiliar().mensajes_error(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaService.save(respuestaModel));
    }

    @Operation(summary = "Actualiza un Elemento Existente", description = "API para Actualizar un Elemento Existente en el Servicio Respuesta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/actualizar-respuesta/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@Valid @RequestBody RespuestaModel respuestaModel, BindingResult result, @PathVariable long id) {
        Optional<RespuestaModel> optional;
        try {
            optional = respuestaService.findById(id);
            if (optional == null || optional.isEmpty());
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        
        if(result.hasErrors()){
            return new Auxiliar().mensajes_error(result);
        }

        RespuestaModel respuestaModel_db = optional.get();
        respuestaModel_db.setTexto(respuestaModel.getTexto());

        return ResponseEntity.status(HttpStatus.OK).body(respuestaService.save(respuestaModel_db));
    }

    @Operation(summary = "Elimina un Elemento Existente", description = "API para Eliminar un Elemento Existente en el Servicio Respuesta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/eliminar-respuesta/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            Optional<RespuestaModel> optional = respuestaService.findById(id);
            if (optional == null || optional.isEmpty());
            respuestaService.deleteById(id);
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok().build();
    }
}