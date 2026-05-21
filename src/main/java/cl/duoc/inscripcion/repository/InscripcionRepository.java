package cl.duoc.inscripcion.repository;

import cl.duoc.inscripcion.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {}
