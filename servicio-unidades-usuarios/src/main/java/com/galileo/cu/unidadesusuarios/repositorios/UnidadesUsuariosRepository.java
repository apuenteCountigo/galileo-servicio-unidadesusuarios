package com.galileo.cu.unidadesusuarios.repositorios;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;


import com.galileo.cu.commons.models.UnidadesUsuarios;

@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "unidadesUsuarios", path = "listar")
public interface UnidadesUsuariosRepository extends PagingAndSortingRepository<UnidadesUsuarios, Long> {
	
	@Query("SELECT u FROM UnidadesUsuarios u WHERE (:idUnidad=0 or u.unidad.id = :idUnidad) "
			+"AND (:idEstado=0 or u.estado.Id=:idEstado) "
			+ "AND (:nombre='' or u.usuario.nombre like %:nombre%) "
			+ "AND (:apellidos='' or u.usuario.apellidos like %:apellidos%) " 
			+ "AND (:tip='' or u.usuario.tip like %:tip%) "
			+ " AND ( "
			+ "			(:idAuth IN (SELECT up FROM Usuarios up WHERE up.perfil.id=1)) "
			+ "	OR ( "
			+ "		(:idAuth IN (SELECT up FROM Usuarios up WHERE up.perfil.id=2)) "
			+ "		AND (u.unidad.id=(SELECT unidad.id FROM UnidadesUsuarios WHERE usuario.id=:idAuth AND estado.id=6)) "
			+ "		) "
			+ ") "
			+ "and ((:fechaFin!=null and :fechaInicio!=null and u.expira between :fechaInicio and :fechaFin) "
			+ "or (:fechaFin=null and :fechaInicio!=null and u.expira >=:fechaInicio) "
			+ "or (:fechaFin=null and :fechaInicio=null)) ")
	public Page<UnidadesUsuarios> filtrarUsuarios(long idAuth,int idUnidad,int idEstado,String tip, String nombre, String apellidos,@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin, Pageable p);
	
	@Query("SELECT u FROM UnidadesUsuarios u WHERE (:idUsuario=0 or u.usuario.id = :idUsuario) "
			+"AND (:idEstado=0 or u.estado.Id=:idEstado) "
			+ " AND ( "
			+ "			(:idAuth IN (SELECT up FROM Usuarios up WHERE up.perfil.id=1)) "
			+ "	OR ( "
			+ "		(:idAuth IN (SELECT up FROM Usuarios up WHERE up.perfil.id=2)) "
			+ "		AND (u.unidad.id=(SELECT unidad.id FROM UnidadesUsuarios WHERE usuario.id=:idAuth AND estado.id=6)) "
			+ "		) "
			+ ") "
			+ "and ((:fechaFin!=null and :fechaInicio!=null and u.expira between :fechaInicio and :fechaFin) "
			+ "or (:fechaFin=null and :fechaInicio!=null and u.expira >=:fechaInicio) "
			+ "or (:fechaFin=null and :fechaInicio=null)) ")
	public Page<UnidadesUsuarios> filtrarUnidades(long idAuth,int idUsuario,int idEstado,@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin, Pageable p);
}