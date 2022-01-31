package mx.unam.dgtic.proyectofinal8.models.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.unam.dgtic.proyectofinal8.models.repository.IArregloMedicionRepository;
import mx.unam.dgtic.proyectofinal8.models.entity.SecuenciaDetalle;
import mx.unam.dgtic.proyectofinal8.models.entity.SecuenciaMedicion;
import mx.unam.dgtic.proyectofinal8.models.repository.IMagnitudArregloRepository;
import mx.unam.dgtic.proyectofinal8.models.repository.ISecuenciaDetalleRepository;
import mx.unam.dgtic.proyectofinal8.models.repository.ISecuenciaMedicionRepository;
import mx.unam.dgtic.proyectofinal8.models.repository.IUsuarioRepository;

@Service
public class SecuenciaServiceImpl implements ISecuenciaService {

	@Autowired
	private ISecuenciaMedicionRepository secuenciaMedicionRepository;

	@Autowired
	private ISecuenciaDetalleRepository secuenciaDetalleRepository;

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Autowired
	private IArregloMedicionRepository arregloMedicionRepository;

	@Autowired
	private IMagnitudArregloRepository magnitudArregloRepository;

	public SecuenciaServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<SecuenciaMedicion> findAll() {
		return secuenciaMedicionRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<SecuenciaMedicion> findById(Integer id) {
		return secuenciaMedicionRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SecuenciaMedicion> findByUsuario(Integer idUsuario) {
		return secuenciaMedicionRepository.findByUsuario(usuarioRepository.findById(idUsuario).get());
	}

	@Override
	@Transactional
	public SecuenciaMedicion insertarSecuencia(SecuenciaMedicion s) {

		SecuenciaMedicion smaux = new SecuenciaMedicion(s.getTitulo(), s.getDescripcion(), LocalDateTime.now());
		smaux.setUsuario(usuarioRepository.findById(s.getUsuario().getIdUsuario()).get());
		SecuenciaMedicion smr = secuenciaMedicionRepository.save(smaux);

//		Set<SecuenciaDetalle> detalles = new HashSet<SecuenciaDetalle>();
		List<SecuenciaDetalle> detalles = new ArrayList<SecuenciaDetalle>();
		if (s.getSecuenciaDetalles() != null) {
			if (s.getSecuenciaDetalles().size() > 0) {
				for (SecuenciaDetalle sd : s.getSecuenciaDetalles()) {
					SecuenciaDetalle sdaux = new SecuenciaDetalle(sd.getNumeroPuntoSecuencia(), sd.getTipoPunto(),
							sd.getDescripcionPunto(), sd.getValor(), sd.getBarridoPrincipal(),
							sd.getBarridoSecundario(), sd.getNumeroMediciones(), sd.getInstrucciones(),
							sd.getComentario(), sd.getDialogoTxt(), sd.getDialogoEntrada(), sd.getDialogoValidacion());
					sdaux.setSecuenciaMedicion(smr);
					if (sd.getArregloMedicion() != null) {
						sdaux.setArregloMedicion(arregloMedicionRepository
								.findById(sd.getArregloMedicion().getIdArregloMedicion()).get());
					}
					if (sd.getMagnitudArreglo() != null) {
						sdaux.setMagnitudArreglo(magnitudArregloRepository
								.findById(sd.getMagnitudArreglo().getIdMagnitudArreglo()).get());
					}
					SecuenciaDetalle sdr = secuenciaDetalleRepository.save(sdaux);
					detalles.add(sdr);
				}
				smr.setSecuenciaDetalles(detalles);

			}
		}

		return smr;
	}

	@Override
	@Transactional
	public SecuenciaMedicion actualizarSecuencia(SecuenciaMedicion s) {

		SecuenciaMedicion smaux = new SecuenciaMedicion(s.getIdSecuenciaMedicion(), s.getTitulo(), s.getDescripcion(),
				LocalDateTime.now());
		smaux.setUsuario(usuarioRepository.findById(s.getUsuario().getIdUsuario()).get());
		SecuenciaMedicion smr = secuenciaMedicionRepository.save(smaux);

		// Set<SecuenciaDetalle> detalles = new HashSet<SecuenciaDetalle>();
		List<SecuenciaDetalle> detalles = new ArrayList<SecuenciaDetalle>();
		if (s.getSecuenciaDetalles() != null) {
			if (s.getSecuenciaDetalles().size() > 0) {
				for (SecuenciaDetalle sd : s.getSecuenciaDetalles()) {
					SecuenciaDetalle sdaux = new SecuenciaDetalle(sd.getIdSecuenciaDetalle(),
							sd.getNumeroPuntoSecuencia(), sd.getTipoPunto(), sd.getDescripcionPunto(), sd.getValor(),
							sd.getBarridoPrincipal(), sd.getBarridoSecundario(), sd.getNumeroMediciones(),
							sd.getInstrucciones(), sd.getComentario(), sd.getDialogoTxt(), sd.getDialogoEntrada(),
							sd.getDialogoValidacion());
					sdaux.setSecuenciaMedicion(smr);
					if (sd.getArregloMedicion() != null) {
						sdaux.setArregloMedicion(arregloMedicionRepository
								.findById(sd.getArregloMedicion().getIdArregloMedicion()).get());
					}
					if (sd.getMagnitudArreglo() != null) {
						sdaux.setMagnitudArreglo(magnitudArregloRepository
								.findById(sd.getMagnitudArreglo().getIdMagnitudArreglo()).get());
					}
					SecuenciaDetalle sdr = secuenciaDetalleRepository.save(sdaux);
					detalles.add(sdr);
				}
				smr.setSecuenciaDetalles(detalles);

			}
		}

		return smr;
	}

	@Override
	@Transactional
	public void borrarSecuenciaPorId(Integer id) {
		SecuenciaMedicion smr = secuenciaMedicionRepository.findById(id).get();

		if (smr.getSecuenciaDetalles() != null && smr.getSecuenciaDetalles().size() > 0) {
			for (SecuenciaDetalle sd : smr.getSecuenciaDetalles()) {
				secuenciaDetalleRepository.deleteById(sd.getIdSecuenciaDetalle());
			}
		}

		secuenciaMedicionRepository.deleteById(smr.getIdSecuenciaMedicion());

	}

}
