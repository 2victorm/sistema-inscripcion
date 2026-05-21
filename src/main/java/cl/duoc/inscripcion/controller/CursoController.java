package cl.duoc.inscripcion.controller;

import cl.duoc.inscripcion.model.Curso;
import cl.duoc.inscripcion.repository.CursoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoRepository cursoRepository;

    public CursoController(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    // GET /cursos - Lista todos los cursos disponibles
    @GetMapping
    public ResponseEntity<List<Curso>> listarCursos() {
        return ResponseEntity.ok(cursoRepository.findAll());
    }

    // POST /cursos - Agrega un nuevo curso
    @PostMapping
    public ResponseEntity<Curso> agregarCurso(@RequestBody Curso curso) {
        Curso guardado = cursoRepository.save(curso);
        return ResponseEntity.ok(guardado);
    }
}
