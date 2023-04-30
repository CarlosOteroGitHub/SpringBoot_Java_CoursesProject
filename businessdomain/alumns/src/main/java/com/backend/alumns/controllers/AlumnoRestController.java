package com.backend.alumns.controllers;

import com.backend.alumns.auxiliar.ApiExceptionHandler;
import com.backend.alumns.models.AlumnoModel;
import com.backend.alumns.services.AlumnoService;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.backend.alumns.auxiliar.Auxiliar;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Tag(name = "API Alumno", description = "API´s del Servicio Alumnos")
@RestController
public class AlumnoRestController {

    @Autowired
    AlumnoService alumnoService;

    @Operation(summary = "Retorna el Template HTML del Servicio", description = "API para Retornar el Template del Servicio Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/alumno-template", method = RequestMethod.GET)
    public ModelAndView alumno(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("alumno.html");
        return modelAndView;
    }

    @Operation(summary = "Retorna un Listado de Elementos con Paginación", description = "API para Retornar el Listado de Elementos con Paginación del Servicio Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/listar-alumnos", method = RequestMethod.GET)
    public ResponseEntity<?> get(Pageable pageable) {
        List<AlumnoModel> lista;
        try {
            lista = (List<AlumnoModel>) alumnoService.findAll();
            if(lista == null || lista.isEmpty());
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(alumnoService.findAll(pageable));
    }

    @Operation(summary = "Retorna el Detalle de un Elemento por ID", description = "API para Retornar el Detalle de un Elemento por ID del Servicio Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/detalle-alumno/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<AlumnoModel> optional;
        try {
            optional = alumnoService.findById(id);
            if (optional == null || optional.isEmpty());
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }

    @Operation(summary = "Retorna el Detalle de la Foto del Alumno por ID", description = "API para Retornar el Detalle de la Foto por ID del Servicio Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/detalle-alumno-archivo/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get_archivo(@PathVariable long id) {
        Optional<AlumnoModel> optional;
        try {
            optional = alumnoService.findById(id);
            if (optional == null || optional.isEmpty());
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }

        Resource imagen = new ByteArrayResource(optional.get().getFoto());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
    }

    @Operation(summary = "Agrega un Nuevo Elemento", description = "API para Agregar un Nuevo Elemento al Servicio Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/agregar-alumno", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody AlumnoModel alumnoModel, BindingResult result) {
        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.save(alumnoModel));
    }

    @Operation(summary = "Agrega una Nueva Foto del Alumno", description = "API para Agregar una Foto al Servicio Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/agregar-archivo-alumno", method = RequestMethod.POST)
    public ResponseEntity<?> post_archivo(@Valid AlumnoModel alumnoModel, BindingResult result, @RequestParam MultipartFile archivo) throws IOException {
        if (!archivo.isEmpty()) {
            alumnoModel.setFoto(archivo.getBytes());
        }
        return this.post(alumnoModel, result);
    }

    @Operation(summary = "Actualiza un Elemento Existente", description = "API para Actualizar un Elemento Existente en el Servicio Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/actualizar-alumno/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@Valid @RequestBody AlumnoModel alumnoModel, BindingResult result, @PathVariable long id) {
        Optional<AlumnoModel> optional;
        try {
            optional = alumnoService.findById(id);
            if (optional == null || optional.isEmpty());
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        
        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }

        AlumnoModel alumnoModel_db = optional.get();
        alumnoModel_db.setNombre(alumnoModel.getNombre());
        alumnoModel_db.setApellido(alumnoModel.getApellido());
        alumnoModel_db.setEmail(alumnoModel.getEmail());
        alumnoModel_db.setSexo(alumnoModel.getSexo());

        return ResponseEntity.status(HttpStatus.OK).body(alumnoService.save(alumnoModel_db));
    }

    @Operation(summary = "Actualiza una Foto Existente del Alumno", description = "API para Actualizar una Foto Existente en el Servicio Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/actualizar-archivo-alumno/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put_archivo(@Valid AlumnoModel alumnoModel, BindingResult result, @PathVariable long id, @RequestParam MultipartFile archivo) throws IOException {
	Optional<AlumnoModel> optional;
        try {
            optional = alumnoService.findById(id);
            if (optional == null || optional.isEmpty());
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        
        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }

        AlumnoModel alumnoModel_db = optional.get();
        alumnoModel_db.setNombre(alumnoModel.getNombre());
        alumnoModel_db.setApellido(alumnoModel.getApellido());
        alumnoModel_db.setEmail(alumnoModel.getEmail());
        alumnoModel_db.setSexo(alumnoModel.getSexo());

        if (!archivo.isEmpty()) {
            alumnoModel.setFoto(archivo.getBytes());
        }

        return ResponseEntity.status(HttpStatus.OK).body(alumnoService.save(alumnoModel_db));
    }

    @Operation(summary = "Elimina un Elemento Existente", description = "API para Eliminar un Elemento Existente en el Servicio Alumno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/eliminar-alumno/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<AlumnoModel> optional;
        try {
            optional = alumnoService.findById(id);
            if (optional == null || optional.isEmpty());
            alumnoService.deleteById(id);
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }
}
