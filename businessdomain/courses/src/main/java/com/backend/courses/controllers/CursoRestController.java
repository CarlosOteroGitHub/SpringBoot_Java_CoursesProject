package com.backend.courses.controllers;

import com.backend.courses.auxiliar.ApiExceptionHandler;
import com.backend.courses.auxiliar.Auxiliar;
import com.backend.courses.auxiliar.HttpClientCommunication;
import com.backend.courses.models.CursoAlumnoModel;
import com.backend.courses.models.CursoModel;
import com.backend.courses.services.CursoService;
import com.fasterxml.jackson.databind.JsonNode;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import reactor.netty.http.client.HttpClient;

@Tag(name = "API Curso", description = "API´s del Servicio Curso")
@RestController
public class CursoRestController {

    private final WebClient.Builder webClientBuilder;

    public CursoRestController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            .responseTimeout(Duration.ofSeconds(1))
            .doOnConnected(connection -> {
                connection.addHandler(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandler(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });

    private String getAlumnoName(Long id) {
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://localhost:8081/detalle-alumno")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8081/detalle-alumno"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();
        String name = block.get("nombre").asText();
        return name;
    }

    @Autowired
    CursoService cursoService;

    @Value("${config.balanceador.test}")
    private String balanceador_test;

    @Operation(summary = "Retorna el Template HTML del Servicio", description = "API para Retornar el Template del Servicio Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/curso-template", method = RequestMethod.GET)
    public ModelAndView curso(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("curso.html");
        return modelAndView;
    }

    @Operation(summary = "Retorna un Listado de Elementos", description = "API para Retornar el Listado de Elementos del Servicio Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/listar-cursos", method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        List<CursoModel> lista = (List<CursoModel>) cursoService.findAll();
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(cursoService.findAll());
    }

    @Operation(summary = "Retorna un Listado de Elementos con Paginación", description = "API para Retornar el Listado de Elementos con Paginación del Servicio Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status - NoContent")
    })
    @RequestMapping(value = "/curso-page", method = RequestMethod.GET)
    public ResponseEntity<?> get_page(Pageable pageable) {
        List<CursoModel> lista = (List<CursoModel>) cursoService.findAll();
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(cursoService.findAll(pageable));
    }

    @Operation(summary = "Retorna un Listado de Elementos con la Variable del Balanceador de Carga", description = "API para Retornar el Listado de Elementos con la Variable del Balanceador de Carga del Servicio Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/curso-test", method = RequestMethod.GET)
    public ResponseEntity<?> get_test() {
        Map<String, Object> response = new HashMap<>();
        response.put("balanceador", this.balanceador_test);
        response.put("cursos", cursoService.findAll());

        if (response == null || response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retorna el Detalle de un Elemento por ID", description = "API para Retornar el Detalle de un Elemento por ID del Servicio Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/detalle-curso/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getID(@PathVariable long id) {
        Optional<CursoModel> optional;
        try {
            optional = cursoService.findById(id);
            if (optional == null || optional.isEmpty());
        } catch (Exception e) {
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }

    @Operation(summary = "Retorna el Detalle de un Elemento por ID con los Alumnos Afiliados", description = "API para Retornar el Detalle de un Elemento por ID del Servicio Curso con los Alumnos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/detalle-curso-alumnos/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCursoAlumnos(@PathVariable long id) {
        Optional<CursoModel> optional = cursoService.findById(id);
        List<CursoAlumnoModel> alumnos = (List<CursoAlumnoModel>) optional.get().getCursoAlumnos();
        alumnos.forEach(x -> {
            String alumnoName = getAlumnoName(x.getAlumnoId());
            x.setAlumnoNombre(alumnoName);
        });
        return ResponseEntity.ok(optional.get());
    }

    @Operation(summary = "Retorna el Detalle de un Elemento por Nombre", description = "API para Retornar el Detalle de un Elemento por Nombre del Servicio Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/buscar-curso/{nombre}", method = RequestMethod.GET)
    public ResponseEntity<?> getNombre(@PathVariable String nombre) {
        Optional<CursoModel> optional;
        try {
            optional = cursoService.findByNombre(nombre);
            if (optional == null || optional.isEmpty());
        } catch (Exception e) {
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok(optional.get());
    }

    @Operation(summary = "Agrega un Nuevo Elemento", description = "API para Agregar un Nuevo Elemento al Servicio Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK")
    })
    @RequestMapping(value = "/agregar-curso", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody CursoModel cursoModel, BindingResult result) {
        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(cursoModel));
    }

    @Operation(summary = "Actualiza un Elemento Existente", description = "API para Actualizar un Elemento Existente en el Servicio Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/actualizar-curso/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@Valid @RequestBody CursoModel cursoModel, BindingResult result, @PathVariable long id) {
        Optional<CursoModel> optional;
        try {
            optional = cursoService.findById(id);
            if (optional == null || optional.isEmpty());
        } catch (Exception e) {
            return new ApiExceptionHandler().handleNotFoundException(e);
        }

        if (result.hasErrors()) {
            return new Auxiliar().mensajes_error(result);
        }

        CursoModel cursoModel_db = optional.get();
        cursoModel_db.setNombre(cursoModel.getNombre());

        return ResponseEntity.status(HttpStatus.OK).body(cursoService.save(cursoModel_db));
    }

    @Operation(summary = "Elimina un Elemento Existente", description = "API para Eliminar un Elemento Existente en el Servicio Curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP Status - OK"),
        @ApiResponse(responseCode = "404", description = "HTTP Status - NotFound")
    })
    @RequestMapping(value = "/eliminar-curso/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            Optional<CursoModel> optional = cursoService.findById(id);
            if (optional == null || optional.isEmpty());
            cursoService.deleteById(id);
        } catch (Exception e) {
            return new ApiExceptionHandler().handleNotFoundException(e);
        }
        return ResponseEntity.ok().build();
    }
}
