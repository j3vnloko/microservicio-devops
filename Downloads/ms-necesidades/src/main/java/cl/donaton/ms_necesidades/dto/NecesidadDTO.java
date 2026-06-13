package cl.donaton.ms_necesidades.dto;

import java.time.LocalDateTime;

import cl.donaton.ms_necesidades.model.Necesidad;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class NecesidadDTO {

    @Data
    public static class CrearRequest {
        @NotBlank(message = "El título es obligatorio")
        private String titulo;
        @NotBlank(message = "La descripción es obligatoria")
        private String descripcion;
        @NotBlank(message = "La ubicación es obligatoria")
        private String ubicacion;
        private Double latitud;
        private Double longitud;
        @NotNull(message = "La categoría es obligatoria")
        private Necesidad.CategoriaRecurso categoria;
        @NotNull(message = "El nivel de urgencia es obligatorio")
        private Necesidad.NivelUrgencia urgencia;
        @NotNull(message = "La cantidad requerida es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        private Integer cantidadRequerida;
        private Necesidad.UnidadMedida unidadMedida;
    }

    @Data
    public static class ActualizarEstadoRequest {
        @NotNull(message = "El estado es obligatorio")
        private Necesidad.EstadoNecesidad estado;
        private Integer cantidadRecibida;
    }

    @Data
    public static class Response {
        private Long id;
        private String titulo;
        private String descripcion;
        private String ubicacion;
        private Double latitud;
        private Double longitud;
        private Necesidad.CategoriaRecurso categoria;
        private Necesidad.NivelUrgencia urgencia;
        private Necesidad.EstadoNecesidad estado;
        private Integer cantidadRequerida;
        private Necesidad.UnidadMedida unidadMedida;
        private Integer cantidadRecibida;
        private Long coordinadorId;
        private LocalDateTime fechaRegistro;
        private LocalDateTime fechaActualizacion;

        public static Response fromEntity(Necesidad n) {
            Response dto = new Response();
            dto.setId(n.getId());
            dto.setTitulo(n.getTitulo());
            dto.setDescripcion(n.getDescripcion());
            dto.setUbicacion(n.getUbicacion());
            dto.setLatitud(n.getLatitud());
            dto.setLongitud(n.getLongitud());
            dto.setCategoria(n.getCategoria());
            dto.setUrgencia(n.getUrgencia());
            dto.setEstado(n.getEstado());
            dto.setCantidadRequerida(n.getCantidadRequerida());
            dto.setUnidadMedida(n.getUnidadMedida());
            dto.setCantidadRecibida(n.getCantidadRecibida());
            dto.setCoordinadorId(n.getCoordinadorId());
            dto.setFechaRegistro(n.getFechaRegistro());
            dto.setFechaActualizacion(n.getFechaActualizacion());
            return dto;
        }
    }
}