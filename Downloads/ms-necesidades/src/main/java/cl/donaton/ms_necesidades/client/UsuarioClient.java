package cl.donaton.ms_necesidades.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios", url = "${ms-usuarios.url}")
public interface UsuarioClient {

    @GetMapping("/usuarios/{id}")
    UsuarioResponseDTO obtenerUsuarioPorId(@PathVariable("id") Long id);
}