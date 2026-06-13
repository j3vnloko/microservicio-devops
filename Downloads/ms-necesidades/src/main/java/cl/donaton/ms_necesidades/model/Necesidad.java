package cl.donaton.ms_necesidades.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "necesidades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Necesidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private String ubicacion;

    private Double latitud;
    private Double longitud;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaRecurso categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelUrgencia urgencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoNecesidad estado;

    @Column(nullable = false)
    private Integer cantidadRequerida;

    @Enumerated(EnumType.STRING)
    private UnidadMedida unidadMedida;

    private Integer cantidadRecibida;

    @Column(nullable = false)
    private Long coordinadorId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        if (estado == null) estado = EstadoNecesidad.PENDIENTE;
        if (cantidadRecibida == null) cantidadRecibida = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    public enum CategoriaRecurso {
        ALIMENTOS, ROPA, MEDICAMENTOS, HIGIENE, HERRAMIENTAS, REFUGIO, OTRO
    }

    public enum NivelUrgencia {
        BAJA, MEDIA, ALTA, CRITICA
    }

    public enum EstadoNecesidad {
        PENDIENTE, EN_PROCESO, CUBIERTA, CERRADA
    }

    public enum UnidadMedida {
        KILOGRAMO, LITRO, UNIDAD, CAJA, BOLSA
    }
}