package com.backend.alumns.controllers;

import com.backend.alumns.auxiliar.Auxiliar;
import com.backend.alumns.models.SexoModel;
import com.backend.alumns.services.SexoService;
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

@RestController
public class SexoRestController {
    
    @Autowired
    SexoService sexoService;
    
    @RequestMapping(value = "/sexo-template", method = RequestMethod.GET)
    public ModelAndView sexo(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sexo.html");
        return modelAndView;
    }

    @RequestMapping(value = "/listar-sexo", method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(sexoService.findAll());
    }
    
    @RequestMapping(value = "/sexo-page", method = RequestMethod.GET)
    public ResponseEntity<?> get_page(Pageable pageable) {
        return ResponseEntity.ok().body(sexoService.findAll(pageable));
    }

    @RequestMapping(value = "/agregar-sexo", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody SexoModel sexoModel, BindingResult result) {
        if(result.hasErrors()){
            return new Auxiliar().mensajes_error(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(sexoService.save(sexoModel));
    }

    @RequestMapping(value = "/detalle-sexo/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<SexoModel> optional = sexoService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @RequestMapping(value = "/actualizar-sexo/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@Valid @RequestBody SexoModel sexoModel, BindingResult result, @PathVariable long id) {
        if(result.hasErrors()){
            return new Auxiliar().mensajes_error(result);
        }
        
        Optional<SexoModel> optional = sexoService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SexoModel sexoModel_db = optional.get();
        sexoModel_db.setNombre(sexoModel.getNombre());

        return ResponseEntity.status(HttpStatus.CREATED).body(sexoService.save(sexoModel_db));
    }

    @RequestMapping(value = "/eliminar-sexo/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        sexoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}