package com.backend.alumns.controllers;

import com.backend.alumns.auxiliar.ApiExceptionHandler;
import com.backend.alumns.auxiliar.Auxiliar;
import com.backend.alumns.models.SexoModel;
import com.backend.alumns.services.SexoService;
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

@Tag(name = "API Sexo", description = "API´s del Servicio Sexo")
@RestController
public class SexoRestController {
    
    @Autowired
    SexoService sexoService;
    
    @Operation(description = "API del Sexo Template", summary = "Retorna el Template HTML del Servicio Sexo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/sexo-template", method = RequestMethod.GET)
    public ModelAndView sexo(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sexo.html");
        return modelAndView;
    }

    @Operation(description = "API del Listado de Sexo", summary = "Retorna el Listado del Servicio Sexo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/listar-sexo", method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        List<SexoModel> lista = (List<SexoModel>) sexoService.findAll();
        if(lista == null || lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(sexoService.findAll());
    }
    
    @Operation(description = "API del Listado Paginado de Sexo", summary = "Retorna el Listado Paginado del Servicio Sexo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/sexo-page", method = RequestMethod.GET)
    public ResponseEntity<?> get_page(Pageable pageable) {
        List<SexoModel> lista = (List<SexoModel>) sexoService.findAll();
        if(lista == null || lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(sexoService.findAll(pageable));
    }
    
    @Operation(description = "API del Detalle de Sexo", summary = "Retorna el Detalle del Servicio Sexo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/detalle-sexo/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<SexoModel> optional;
        try {
            optional = sexoService.findById(id);
            if (optional == null || optional.isEmpty());
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }

    @Operation(description = "API para Agregar un Registro de Sexo", summary = "Agrega un Registro al Servicio Sexo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/agregar-sexo", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody SexoModel sexoModel, BindingResult result) {
        if(result.hasErrors()){
            return new Auxiliar().mensajes_error(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(sexoService.save(sexoModel));
    }

    @Operation(description = "API para Actualizar un Registro de Sexo", summary = "Actualiza un Registro al Servicio Sexo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/actualizar-sexo/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@Valid @RequestBody SexoModel sexoModel, BindingResult result, @PathVariable long id) {
        Optional<SexoModel> optional = sexoService.findById(id);
        if (optional == null || optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        if(result.hasErrors()){
            return new Auxiliar().mensajes_error(result);
        }

        SexoModel sexoModel_db = optional.get();
        sexoModel_db.setNombre(sexoModel.getNombre());

        return ResponseEntity.status(HttpStatus.OK).body(sexoService.save(sexoModel_db));
    }

    @Operation(description = "API para Eliminar un Registro de Sexo", summary = "Elimina un Registro al Servicio Sexo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/eliminar-sexo/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            Optional<SexoModel> optional = sexoService.findById(id);
            if (optional == null || optional.isEmpty());
            sexoService.deleteById(id);
        } catch(Exception e){
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok().build();
    }
}