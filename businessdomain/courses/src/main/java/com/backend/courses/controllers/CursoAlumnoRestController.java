package com.backend.courses.controllers;

import com.backend.courses.auxiliar.ApiExceptionHandler;
import com.backend.courses.auxiliar.Auxiliar;
import com.backend.courses.components.Alumnos;
import com.backend.courses.models.CursoAlumnoModel;
import com.backend.courses.services.CursoAlumnoService;
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

@Tag(name = "API Cursos - Alumnos", description = "API´s del Servicio Cursos - Alumnos")
@RestController
public class CursoAlumnoRestController {

    @Autowired
    CursoAlumnoService cursoAlumnoService;

    @Operation(summary = "Retorna el Template HTML del Servicio", description = "API para Retornar el Template del Servicio Curso y Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/curso-alumno-template", method = RequestMethod.GET)
    public ModelAndView cursoAlumno(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cursoAlumno.html");
        return modelAndView;
    }

    @Operation(summary = "Retorna un Listado de Elementos con Paginación", description = "API para Retornar el Listado de Elementos con Paginación del Servicio Curso y Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/listar-curso-alumno", method = RequestMethod.GET)
    public ResponseEntity<?> get(Pageable pageable) {
        List<CursoAlumnoModel> lista;
        try {
            lista = (List<CursoAlumnoModel>) cursoAlumnoService.findAll();
            if (lista == null || lista.isEmpty());
            lista.forEach(x -> {
                String alumnoName = Alumnos.getInstance().getAlumnoName(x.getAlumnoId().intValue());
                x.setAlumnoNombre(alumnoName);
            });
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(cursoAlumnoService.findAll(pageable));
    }

    @Operation(summary = "Retorna el Detalle de un Elemento por ID", description = "API para Retornar el Detalle de un Elemento por ID del Servicio Curso y Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/detalle-curso-alumno/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<CursoAlumnoModel> optional;
        try {
            optional = cursoAlumnoService.findById(id);
            if (optional == null || optional.isEmpty());
            List<CursoAlumnoModel> alumnos = (List<CursoAlumnoModel>) optional.get().getCurso().getCursoAlumnos();
            alumnos.forEach(x -> {
                String alumnoName = Alumnos.getInstance().getAlumnoName(x.getAlumnoId().intValue());
                x.setAlumnoNombre(alumnoName);
            });
        } catch (Exception e) {
            return ApiExceptionHandler.getInstance().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }

    @Operation(summary = "Retorna el Detalle de un Elemento por ID del Alumno", description = "API para Retornar el Detalle de un Elemento por ID del Alumno del Servicio Curso y Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/buscar-curso-alumno/{id_alumno}", method = RequestMethod.GET)
    public ResponseEntity<?> getIDAlumno(@PathVariable long alumnoId) {
        Optional<CursoAlumnoModel> optional;
        try {
            optional = cursoAlumnoService.findByIdAlumno(alumnoId);
            if (optional == null || optional.isEmpty());
        } catch (Exception e) {
            return ApiExceptionHandler.getInstance().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }

    @Operation(summary = "Agrega un Nuevo Elemento", description = "API para Agregar un Nuevo Elemento al Servicio Curso y Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/agregar-curso-alumno", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody CursoAlumnoModel cursoAlumnoModel, BindingResult result) {
        if(Alumnos.getInstance().validIdAlumno(cursoAlumnoModel.getAlumnoId().intValue()));
        
        if (result.hasErrors()) {
            return Auxiliar.getInstance().mensajes_error(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoAlumnoService.save(cursoAlumnoModel));
    }
    
    @Operation(summary = "Actualiza un Elemento Existente", description = "API para Actualizar un Elemento Existente en el Servicio Curso y Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/actualizar-curso-alumno/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@Valid @RequestBody CursoAlumnoModel cursoAlumnoModel, BindingResult result, @PathVariable long id) {
        Optional<CursoAlumnoModel> optional;
        try {
            optional = cursoAlumnoService.findById(id);
            if (optional == null || optional.isEmpty());
        } catch (Exception e) {
            return ApiExceptionHandler.getInstance().handleNotFoundException(e);
        }
        
        if(Alumnos.getInstance().validIdAlumno(cursoAlumnoModel.getAlumnoId().intValue()));

        if (result.hasErrors()) {
            return Auxiliar.getInstance().mensajes_error(result);
        }

        CursoAlumnoModel cursoAlumnoModel_db = optional.get();
        cursoAlumnoModel_db.setAlumnoId(cursoAlumnoModel.getAlumnoId());
        
        return ResponseEntity.status(HttpStatus.OK).body(cursoAlumnoService.save(cursoAlumnoModel_db));
    }

    @Operation(summary = "Elimina un Elemento Existente", description = "API para Eliminar un Elemento Existente en el Servicio Curso y Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/eliminar-curso-alumno/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<CursoAlumnoModel> optional;
        try {
            optional = cursoAlumnoService.findById(id);
            if (optional == null || optional.isEmpty());
            cursoAlumnoService.deleteById(id);
        } catch (Exception e) {
            return ApiExceptionHandler.getInstance().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }
}
