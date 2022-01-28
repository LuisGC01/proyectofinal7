package mx.unam.dgtic.proyectofinal7.models.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import mx.unam.dgtic.proyectofinal7.models.entity.SecuenciaMedicion;
import mx.unam.dgtic.proyectofinal7.models.entity.Usuario;

public interface ISecuenciaMedicionRepository extends CrudRepository<SecuenciaMedicion, Integer> {
	public abstract List<SecuenciaMedicion> findByUsuario(Usuario usuario);
}
