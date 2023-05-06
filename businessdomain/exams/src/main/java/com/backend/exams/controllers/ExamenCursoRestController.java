package com.backend.exams.controllers;

import com.backend.exams.auxiliar.ApiExceptionHandler;
import com.backend.exams.auxiliar.Auxiliar;
import com.backend.exams.components.Cursos;
import com.backend.exams.models.ExamenCursoModel;
import com.backend.exams.services.ExamenCursoService;
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

@Tag(name = "API Examenes - Cursos", description = "API´s del Servicio Examenes - Cursos")
@RestController
public class ExamenCursoRestController {
    
    @Autowired
    ExamenCursoService examenCursoService;

    @Operation(summary = "Retorna el Template HTML del Servicio", description = "API para Retornar el Template del Servicio Examen y Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/examen-curso-template", method = RequestMethod.GET)
    public ModelAndView examenCurso(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("examenCurso.html");
        return modelAndView;
    }
    
    @Operation(summary = "Retorna un Listado de Elementos con Paginación", description = "API para Retornar el Listado de Elementos con Paginación del Servicio Examen y Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/listar-examen-curso", method = RequestMethod.GET)
    public ResponseEntity<?> get(Pageable pageable) {
        List<ExamenCursoModel> lista;
        try {
            lista = (List<ExamenCursoModel>) examenCursoService.findAll();
            if (lista == null || lista.isEmpty());
            lista.forEach(x -> {
                String cursoName = Cursos.getInstance().getCursoName(x.getCursoId().intValue());
                x.setCursoNombre(cursoName);
            });
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(examenCursoService.findAll(pageable));
    }
    
    @Operation(summary = "Retorna el Detalle de un Elemento por ID", description = "API para Retornar el Detalle de un Elemento por ID del Servicio Examen y Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/detalle-examen-curso/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<ExamenCursoModel> optional;
        try {
            optional = examenCursoService.findById(id);
            if (optional == null || optional.isEmpty());
            List<ExamenCursoModel> alumnos = (List<ExamenCursoModel>) optional.get().getExamen().getExamenCursos();
            alumnos.forEach(x -> {
                String cursoName = Cursos.getInstance().getCursoName(x.getCursoId().intValue());
                x.setCursoNombre(cursoName);
            });
        } catch (Exception e) {
            return ApiExceptionHandler.getInstance().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }

    @Operation(summary = "Agrega un Nuevo Elemento", description = "API para Agregar un Nuevo Elemento al Servicio Examen y Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/agregar-examen-curso", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody ExamenCursoModel cursoAlumnoModel, BindingResult result) {
        if(Cursos.getInstance().validIdCurso(cursoAlumnoModel.getCursoId().intValue()));
        
        if (result.hasErrors()) {
            return Auxiliar.getInstance().mensajes_error(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(examenCursoService.save(cursoAlumnoModel));
    }
    
    @Operation(summary = "Actualiza un Elemento Existente", description = "API para Actualizar un Elemento Existente en el Servicio Examen y Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/actualizar-examen-curso/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@Valid @RequestBody ExamenCursoModel cursoAlumnoModel, BindingResult result, @PathVariable long id) {
        Optional<ExamenCursoModel> optional;
        try {
            optional = examenCursoService.findById(id);
            if (optional == null || optional.isEmpty());
        } catch (Exception e) {
            return ApiExceptionHandler.getInstance().handleNotFoundException(e);
        }
        
        if(Cursos.getInstance().validIdCurso(cursoAlumnoModel.getCursoId().intValue()));
        
        if (result.hasErrors()) {
            return Auxiliar.getInstance().mensajes_error(result);
        }

        ExamenCursoModel examenCursoModel_db = optional.get();
        examenCursoModel_db.setCursoId(cursoAlumnoModel.getCursoId());
        
        return ResponseEntity.status(HttpStatus.OK).body(examenCursoService.save(examenCursoModel_db));
    }

    @Operation(summary = "Elimina un Elemento Existente", description = "API para Eliminar un Elemento Existente en el Servicio Examen y Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/eliminar-examen-curso/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<ExamenCursoModel> optional;
        try {
            optional = examenCursoService.findById(id);
            if (optional == null || optional.isEmpty());
            examenCursoService.deleteById(id);
        } catch (Exception e) {
            return ApiExceptionHandler.getInstance().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }
}