package com.galileo.cu.unidadesusuarios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galileo.cu.commons.models.UnidadesUsuarios;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpiraUserRepository extends JpaRepository<UnidadesUsuarios, Long> {
    List<UnidadesUsuarios> findByExpiraBefore(LocalDateTime dateTime);
}
