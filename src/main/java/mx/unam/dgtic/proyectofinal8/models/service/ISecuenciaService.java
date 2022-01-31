package mx.unam.dgtic.proyectofinal8.models.service;

import java.util.List;
import java.util.Optional;

import mx.unam.dgtic.proyectofinal8.models.entity.SecuenciaMedicion;

public interface ISecuenciaService {

	public abstract Iterable<SecuenciaMedicion> findAll();

	public abstract Optional<SecuenciaMedicion> findById(Integer id);

	public abstract SecuenciaMedicion insertarSecuencia(SecuenciaMedicion s);

	public abstract SecuenciaMedicion actualizarSecuencia(SecuenciaMedicion s);

	public abstract void borrarSecuenciaPorId(Integer id);

	public abstract List<SecuenciaMedicion> findByUsuario(Integer idUsuario);


}
