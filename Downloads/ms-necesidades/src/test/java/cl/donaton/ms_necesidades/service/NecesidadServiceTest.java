package cl.donaton.ms_necesidades.service;

import cl.donaton.ms_necesidades.dto.NecesidadDTO;
import cl.donaton.ms_necesidades.model.Necesidad;
import cl.donaton.ms_necesidades.repository.NecesidadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NecesidadServiceTest {

    @Mock
    private NecesidadRepository necesidadRepository;

    @InjectMocks
    private NecesidadService necesidadService;

    private Necesidad necesidadMock;

    @BeforeEach
    void setUp() {
        necesidadMock = new Necesidad();
        necesidadMock.setId(1L);
        necesidadMock.setTitulo("Alimentos urgentes");
        necesidadMock.setDescripcion("Se necesitan cajas de alimentos no perecibles");
        necesidadMock.setUbicacion("Santiago Centro");
        necesidadMock.setCategoria(Necesidad.CategoriaRecurso.ALIMENTOS);
        necesidadMock.setUrgencia(Necesidad.NivelUrgencia.ALTA);
        necesidadMock.setEstado(Necesidad.EstadoNecesidad.PENDIENTE);
        necesidadMock.setCantidadRequerida(100);
        necesidadMock.setCantidadRecibida(0);
        necesidadMock.setCoordinadorId(5L);
    }

    @Test
    void crear_debeRetornarNecesidadCreada() {
        NecesidadDTO.CrearRequest request = new NecesidadDTO.CrearRequest();
        request.setTitulo("Alimentos urgentes");
        request.setDescripcion("Se necesitan cajas de alimentos no perecibles");
        request.setUbicacion("Santiago Centro");
        request.setCategoria(Necesidad.CategoriaRecurso.ALIMENTOS);
        request.setUrgencia(Necesidad.NivelUrgencia.ALTA);
        request.setCantidadRequerida(100);
        when(necesidadRepository.save(any(Necesidad.class))).thenReturn(necesidadMock);
        NecesidadDTO.Response result = necesidadService.crear(request, 5L);
        assertNotNull(result);
        assertEquals("Alimentos urgentes", result.getTitulo());
        verify(necesidadRepository, times(1)).save(any(Necesidad.class));
    }

    @Test
    void buscarPorId_debeRetornarNecesidad_cuandoExiste() {
        when(necesidadRepository.findById(1L)).thenReturn(Optional.of(necesidadMock));
        NecesidadDTO.Response result = necesidadService.buscarPorId(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void buscarPorId_debeLanzarExcepcion_cuandoNoExiste() {
        when(necesidadRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> necesidadService.buscarPorId(99L));
    }

    @Test
    void listarTodas_debeRetornarListaCompleta() {
        when(necesidadRepository.findAll()).thenReturn(List.of(necesidadMock));
        List<NecesidadDTO.Response> result = necesidadService.listarTodas();
        assertEquals(1, result.size());
    }

    @Test
    void actualizarEstado_debeCambiarEstadoCorrectamente() {
        NecesidadDTO.ActualizarEstadoRequest request = new NecesidadDTO.ActualizarEstadoRequest();
        request.setEstado(Necesidad.EstadoNecesidad.EN_PROCESO);
        request.setCantidadRecibida(50);
        necesidadMock.setEstado(Necesidad.EstadoNecesidad.EN_PROCESO);
        necesidadMock.setCantidadRecibida(50);
        when(necesidadRepository.findById(1L)).thenReturn(Optional.of(necesidadMock));
        when(necesidadRepository.save(any(Necesidad.class))).thenReturn(necesidadMock);
        NecesidadDTO.Response result = necesidadService.actualizarEstado(1L, request);
        assertEquals(Necesidad.EstadoNecesidad.EN_PROCESO, result.getEstado());
    }

    @Test
    void eliminar_debeEliminarCorrectamente_cuandoExiste() {
        when(necesidadRepository.existsById(1L)).thenReturn(true);
        doNothing().when(necesidadRepository).deleteById(1L);
        assertDoesNotThrow(() -> necesidadService.eliminar(1L));
        verify(necesidadRepository, times(1)).deleteById(1L);
    }
}