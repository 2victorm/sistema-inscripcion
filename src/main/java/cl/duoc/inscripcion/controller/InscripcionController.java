package cl.duoc.inscripcion.controller;

import cl.duoc.inscripcion.model.Curso;
import cl.duoc.inscripcion.model.Inscripcion;
import cl.duoc.inscripcion.repository.CursoRepository;
import cl.duoc.inscripcion.repository.InscripcionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    private final CursoRepository cursoRepository;
    private final InscripcionRepository inscripcionRepository;

    public InscripcionController(CursoRepository cursoRepository,
                                  InscripcionRepository inscripcionRepository) {
        this.cursoRepository = cursoRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    // POST /inscripciones
    // Body: { "nombreEstudiante": "Juan", "cursosIds": [1, 2] }
    @PostMapping
    public ResponseEntity<Map<String, Object>> inscribir(@RequestBody Map<String, Object> body) {

        String nombreEstudiante = (String) body.get("nombreEstudiante");

        @SuppressWarnings("unchecked")
        List<Integer> cursosIds = (List<Integer>) body.get("cursosIds");

        List<Curso> cursosSeleccionados = cursosIds.stream()
                .map(id -> cursoRepository.findById(Long.valueOf(id))
                        .orElseThrow(() -> new RuntimeException("Curso no encontrado: " + id)))
                .toList();

        double total = cursosSeleccionados.stream()
                .mapToDouble(Curso::getCosto)
                .sum();

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setNombreEstudiante(nombreEstudiante);
        inscripcion.setCursos(cursosSeleccionados);
        inscripcion.setTotalPagar(total);
        inscripcionRepository.save(inscripcion);

        // Resumen de respuesta
        Map<String, Object> resumen = new HashMap<>();
        resumen.put("inscripcionId", inscripcion.getId());
        resumen.put("estudiante", nombreEstudiante);
        resumen.put("cursos", cursosSeleccionados);
        resumen.put("totalPagar", total);

        return ResponseEntity.ok(resumen);
    }
}
