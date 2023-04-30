package com.backend.exams.controllers;

import com.backend.exams.auxiliar.ApiExceptionHandler;
import com.backend.exams.auxiliar.Auxiliar;
import com.backend.exams.models.ExamenModel;
import com.backend.exams.services.ExamenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Tag(name = "API Examen", description = "API´s del Servicio Examenes")
@RestController
public class ExamenRestController {

    @Autowired
    ExamenService examenService;
    
    @Operation(summary = "Retorna el Template HTML del Servicio", description = "API para Retornar el Template del Servicio Examen")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/examen-template", method = RequestMethod.GET)
    public ModelAndView examen(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("examen.html");
        return modelAndView;
    }

    @Operation(summary = "Retorna un Listado de Elementos con Paginación", description = "API para Retornar el Listado de Elementos con Paginación del Servicio Examen")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/listar-examenes", method = RequestMethod.GET)
    public ResponseEntity<?> get(Pageable pageable) {
        List<ExamenModel> lista;
        try {
            lista = (List<ExamenModel>) examenService.findAll();
            if(lista == null || lista.isEmpty());
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(examenService.findAll(pageable));
    }
    
    @Operation(summary = "Retorna el Detalle de un Elemento por ID", description = "API para Retornar el Detalle de un Elemento por ID del Servicio Examen")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/detalle-examen/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<ExamenModel> optional;
        try {
            optional = examenService.findById(id);
            if (optional == null || optional.isEmpty());
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }

    @Operation(summary = "Agrega un Nuevo Elemento", description = "API para Agregar un Nuevo Elemento al Servicio Examen")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/agregar-examen", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody ExamenModel examenModel, BindingResult result) {
        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(examenService.save(examenModel));
    }

    @Operation(summary = "Actualiza un Elemento Existente", description = "API para Actualizar un Elemento Existente en el Servicio Examen")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/actualizar-examen/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@Valid @RequestBody ExamenModel examenModel, BindingResult result, @PathVariable long id) {
        Optional<ExamenModel> optional;
        try {
            optional = examenService.findById(id);
            if (optional == null || optional.isEmpty());
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }

        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }

        ExamenModel examenModel_db = optional.get();
        examenModel_db.setNombre(examenModel.getNombre());
        examenModel_db.setRespondido(examenModel.isRespondido());
        
        examenModel_db.getPreguntas().stream().filter(pdb -> examenModel.getPreguntas().contains(pdb)).forEach(p -> {
            examenModel_db.removePregunta(p);
        });

        examenModel_db.setPreguntas(examenModel.getPreguntas());

        return ResponseEntity.status(HttpStatus.OK).body(examenService.save(examenModel_db));
    }

    @Operation(summary = "Elimina un Elemento Existente", description = "API para Eliminar un Elemento Existente en el Servicio Examen")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/eliminar-examen/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<ExamenModel> optional;
        try {
            optional = examenService.findById(id);
            if (optional == null || optional.isEmpty());
            examenService.deleteById(id);
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }
}