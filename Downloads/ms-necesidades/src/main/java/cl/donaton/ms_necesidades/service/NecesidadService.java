package cl.donaton.ms_necesidades.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cl.donaton.ms_necesidades.client.UsuarioClient;
import cl.donaton.ms_necesidades.client.UsuarioResponseDTO;
import cl.donaton.ms_necesidades.dto.NecesidadDTO;
import cl.donaton.ms_necesidades.model.Necesidad;
import cl.donaton.ms_necesidades.repository.NecesidadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NecesidadService {

    private final NecesidadRepository necesidadRepository;
    private final UsuarioClient usuarioClient;

    public NecesidadDTO.Response crear(NecesidadDTO.CrearRequest request, Long coordinadorId) {
        UsuarioResponseDTO coordinador = usuarioClient.obtenerUsuarioPorId(coordinadorId);
        log.info("Coordinador validado: {} ({})", coordinador.getNombre(), coordinador.getEmail());

        Necesidad necesidad = new Necesidad();
        necesidad.setTitulo(request.getTitulo());
        necesidad.setDescripcion(request.getDescripcion());
        necesidad.setUbicacion(request.getUbicacion());
        necesidad.setLatitud(request.getLatitud());
        necesidad.setLongitud(request.getLongitud());
        necesidad.setCategoria(request.getCategoria());
        necesidad.setUrgencia(request.getUrgencia());
        necesidad.setCantidadRequerida(request.getCantidadRequerida());
        necesidad.setUnidadMedida(request.getUnidadMedida());
        necesidad.setCoordinadorId(coordinadorId);
        Necesidad guardada = necesidadRepository.save(necesidad);
        log.info("Necesidad creada con ID: {}", guardada.getId());
        return NecesidadDTO.Response.fromEntity(guardada);
    }

    public List<NecesidadDTO.Response> listarTodas() {
        return necesidadRepository.findAll().stream()
                .map(NecesidadDTO.Response::fromEntity).collect(Collectors.toList());
    }

    public NecesidadDTO.Response buscarPorId(Long id) {
        Necesidad necesidad = necesidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Necesidad no encontrada con ID: " + id));
        return NecesidadDTO.Response.fromEntity(necesidad);
    }

    public NecesidadDTO.Response actualizarEstado(Long id, NecesidadDTO.ActualizarEstadoRequest request) {
        Necesidad necesidad = necesidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Necesidad no encontrada con ID: " + id));
        necesidad.setEstado(request.getEstado());
        if (request.getCantidadRecibida() != null) {
            necesidad.setCantidadRecibida(request.getCantidadRecibida());
        }
        return NecesidadDTO.Response.fromEntity(necesidadRepository.save(necesidad));
    }

    public List<NecesidadDTO.Response> filtrarPorEstado(Necesidad.EstadoNecesidad estado) {
        return necesidadRepository.findByEstado(estado).stream()
                .map(NecesidadDTO.Response::fromEntity).collect(Collectors.toList());
    }

    public List<NecesidadDTO.Response> filtrarPorUrgencia(Necesidad.NivelUrgencia urgencia) {
        return necesidadRepository.findByUrgencia(urgencia).stream()
                .map(NecesidadDTO.Response::fromEntity).collect(Collectors.toList());
    }

    public List<NecesidadDTO.Response> filtrarPorCategoria(Necesidad.CategoriaRecurso categoria) {
        return necesidadRepository.findByCategoria(categoria).stream()
                .map(NecesidadDTO.Response::fromEntity).collect(Collectors.toList());
    }

    public List<NecesidadDTO.Response> listarCriticasPendientes() {
        return necesidadRepository.findByUrgenciaAndEstado(
                Necesidad.NivelUrgencia.CRITICA, Necesidad.EstadoNecesidad.PENDIENTE).stream()
                .map(NecesidadDTO.Response::fromEntity).collect(Collectors.toList());
    }

    public List<NecesidadDTO.Response> listarPorCoordinador(Long coordinadorId) {
        return necesidadRepository.findByCoordinadorId(coordinadorId).stream()
                .map(NecesidadDTO.Response::fromEntity).collect(Collectors.toList());
    }

    public void eliminar(Long id) {
        if (!necesidadRepository.existsById(id))
            throw new RuntimeException("Necesidad no encontrada con ID: " + id);
        necesidadRepository.deleteById(id);
    }
}