package com.backend.courses.controllers;

import com.backend.courses.models.CursoModel;
import com.backend.courses.services.CursoService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CursoRestController {

    @Autowired
    CursoService cursoService;
    
    @Value("${config.balanceador.test}")
    private String balanceador_test;
    
    @RequestMapping(value = "/curso-template", method = RequestMethod.GET)
    public ModelAndView curso(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("curso.html");
        return modelAndView;
    }

    @RequestMapping(value = "/curso-test", method = RequestMethod.GET)
    public ResponseEntity<?> get_test() {
        Map<String, Object> response = new HashMap<>();
        response.put("balanceador", this.balanceador_test);
        response.put("cursos", cursoService.findAll());
        return ResponseEntity.ok(response);
    }
    
    @RequestMapping(value = "/listar-cursos", method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(cursoService.findAll());
    }

    @RequestMapping(value = "/agregar-curso", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody CursoModel cursoModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(cursoModel));
    }
    
    @RequestMapping(value = "/curso-page", method = RequestMethod.GET)
    public ResponseEntity<?> get_page(Pageable pageable) {
        return ResponseEntity.ok().body(cursoService.findAll(pageable));
    }

    @RequestMapping(value = "/detalle-curso/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<CursoModel> optional = cursoService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @RequestMapping(value = "/actualizar-curso/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@PathVariable long id, @RequestBody CursoModel cursoModel) {
        Optional<CursoModel> optional = cursoService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CursoModel cursoModel_db = optional.get();
        cursoModel_db.setNombre(cursoModel.getNombre());

        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(cursoModel_db));
    }

    @RequestMapping(value = "/eliminar-curso/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        cursoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
