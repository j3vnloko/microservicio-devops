package cl.donaton.ms_necesidades.repository;

import cl.donaton.ms_necesidades.model.Necesidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NecesidadRepository extends JpaRepository<Necesidad, Long> {
    List<Necesidad> findByEstado(Necesidad.EstadoNecesidad estado);
    List<Necesidad> findByUrgencia(Necesidad.NivelUrgencia urgencia);
    List<Necesidad> findByCategoria(Necesidad.CategoriaRecurso categoria);
    List<Necesidad> findByCoordinadorId(Long coordinadorId);
    List<Necesidad> findByUbicacionContainingIgnoreCase(String ubicacion);
    List<Necesidad> findByUrgenciaAndEstado(Necesidad.NivelUrgencia urgencia, Necesidad.EstadoNecesidad estado);
}