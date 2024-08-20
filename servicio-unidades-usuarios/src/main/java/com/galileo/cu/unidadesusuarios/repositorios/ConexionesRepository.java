package com.galileo.cu.unidadesusuarios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.galileo.cu.commons.models.Conexiones;
import java.util.List;

public interface ConexionesRepository extends JpaRepository<Conexiones, Long> {
    List<Conexiones> findByServicio(String servicio);
}
