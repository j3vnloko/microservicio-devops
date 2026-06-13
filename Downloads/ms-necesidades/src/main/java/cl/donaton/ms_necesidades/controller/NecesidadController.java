package cl.donaton.ms_necesidades.controller;

import cl.donaton.ms_necesidades.dto.NecesidadDTO;
import cl.donaton.ms_necesidades.model.Necesidad;
import cl.donaton.ms_necesidades.service.NecesidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/necesidades")
@RequiredArgsConstructor
public class NecesidadController {

    private final NecesidadService necesidadService;

    @PostMapping
    public ResponseEntity<NecesidadDTO.Response> crear(
            @Valid @RequestBody NecesidadDTO.CrearRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long coordinadorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(necesidadService.crear(request, coordinadorId));
    }

    @GetMapping
    public ResponseEntity<List<NecesidadDTO.Response>> listarTodas() {
        return ResponseEntity.ok(necesidadService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NecesidadDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(necesidadService.buscarPorId(id));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<NecesidadDTO.Response> actualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody NecesidadDTO.ActualizarEstadoRequest request) {
        return ResponseEntity.ok(necesidadService.actualizarEstado(id, request));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<NecesidadDTO.Response>> filtrarPorEstado(
            @PathVariable Necesidad.EstadoNecesidad estado) {
        return ResponseEntity.ok(necesidadService.filtrarPorEstado(estado));
    }

    @GetMapping("/urgencia/{urgencia}")
    public ResponseEntity<List<NecesidadDTO.Response>> filtrarPorUrgencia(
            @PathVariable Necesidad.NivelUrgencia urgencia) {
        return ResponseEntity.ok(necesidadService.filtrarPorUrgencia(urgencia));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<NecesidadDTO.Response>> filtrarPorCategoria(
            @PathVariable Necesidad.CategoriaRecurso categoria) {
        return ResponseEntity.ok(necesidadService.filtrarPorCategoria(categoria));
    }

    @GetMapping("/criticas")
    public ResponseEntity<List<NecesidadDTO.Response>> listarCriticasPendientes() {
        return ResponseEntity.ok(necesidadService.listarCriticasPendientes());
    }

    @GetMapping("/coordinador/{coordinadorId}")
    public ResponseEntity<List<NecesidadDTO.Response>> listarPorCoordinador(
            @PathVariable Long coordinadorId) {
        return ResponseEntity.ok(necesidadService.listarPorCoordinador(coordinadorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        necesidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}