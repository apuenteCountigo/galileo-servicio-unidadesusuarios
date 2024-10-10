package com.galileo.cu.unidadesusuarios.repositorios;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.galileo.cu.commons.models.UnidadesUsuarios;

@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "unidadesUsuarios", path = "listar")
public interface UnidadesUsuariosRepository extends PagingAndSortingRepository<UnidadesUsuarios, Long> {

	@Query("SELECT u FROM UnidadesUsuarios u WHERE (:idUnidad = 0 OR u.unidad.id = :idUnidad) "
			+ "AND (:idEstado = 0 OR u.estado.Id = :idEstado) "
			+ "AND (:nombre = '' OR u.usuario.nombre LIKE CONCAT('%', :nombre, '%')) "
			+ "AND (:apellidos = '' OR u.usuario.apellidos LIKE CONCAT('%', :apellidos, '%')) "
			+ "AND (:tip = '' OR u.usuario.tip LIKE CONCAT('%', :tip, '%')) "
			+ "AND ( "
			+ "     (:idAuth IN (SELECT up.id FROM Usuarios up WHERE up.perfil.id = 1)) "
			+ "  OR ( "
			+ "     (:idAuth IN (SELECT up.id FROM Usuarios up WHERE up.perfil.id = 2)) "
			+ "     AND (u.unidad.id = (SELECT uu.unidad.id FROM UnidadesUsuarios uu WHERE uu.usuario.id = :idAuth AND uu.estado.id = 6)) "
			+ "     ) "
			+ ") "
			+ "AND ( "
			+ "     (:fechaFin IS NOT NULL AND :fechaInicio IS NOT NULL AND u.expira BETWEEN :fechaInicio AND :fechaFin) "
			+ "  OR (:fechaFin IS NULL AND :fechaInicio IS NOT NULL AND u.expira >= :fechaInicio) "
			+ "  OR (:fechaFin IS NULL AND :fechaInicio IS NULL) "
			+ ") ")
	public Page<UnidadesUsuarios> filtrarUsuarios(long idAuth, int idUnidad, int idEstado, String tip,
			String nombre, String apellidos, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable p);

	@Query("SELECT u FROM UnidadesUsuarios u WHERE (:idUsuario = 0 OR u.usuario.id = :idUsuario) "
			+ "AND (:idEstado = 0 OR u.estado.Id = :idEstado) "
			+ "AND ( "
			+ "     (:idAuth IN (SELECT up.id FROM Usuarios up WHERE up.perfil.id = 1)) "
			+ "  OR ( "
			+ "     (:idAuth IN (SELECT up.id FROM Usuarios up WHERE up.perfil.id = 2)) "
			+ "     AND (u.unidad.id = (SELECT uu.unidad.id FROM UnidadesUsuarios uu WHERE uu.usuario.id = :idAuth AND uu.estado.id = 6)) "
			+ "     ) "
			+ ") "
			+ "AND ( "
			+ "     (:fechaFin IS NOT NULL AND :fechaInicio IS NOT NULL AND u.expira BETWEEN :fechaInicio AND :fechaFin) "
			+ "  OR (:fechaFin IS NULL AND :fechaInicio IS NOT NULL AND u.expira >= :fechaInicio) "
			+ "  OR (:fechaFin IS NULL AND :fechaInicio IS NULL) "
			+ ") ")
	public Page<UnidadesUsuarios> filtrarUnidades(long idAuth, int idUsuario, int idEstado,
			LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable p);
}