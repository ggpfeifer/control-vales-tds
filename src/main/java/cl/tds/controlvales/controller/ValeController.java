/**
 * 
 */
package cl.tds.controlvales.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cl.tds.controlvales.beans.CentroCosto;
import cl.tds.controlvales.beans.Estado;
import cl.tds.controlvales.beans.Usuario;
import cl.tds.controlvales.beans.Vale;
import cl.tds.controlvales.daos.ValeDAO;
import cl.tds.controlvales.util.Folio;
import cl.tds.controlvales.util.ValidacionUtil;

/**
 * @author "Fernando Valencia"
 *
 */
public class ValeController {

	ValeDAO valeDao = new ValeDAO();
	
	/** Verifica que un vale tenga los campos necesarios y lo almacena en la base de datos
	 * 
	 * @param vale el vale
	 * @return {@code true} si la solicitud es positiva, {@code false} en caso contrario
	 */
	public boolean solicitaVale( Vale vale ){
		boolean resp = false;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = df.format(vale.getFecha_uso());
		if( vale.getFecha_uso() != null 
				&& vale.getOrigen() != null
				&& vale.getDestino() != null
				&& vale.getMonto_estipulado() != 0
				&& vale.getMotivo_viaje() != null 
				&& ValidacionUtil.validaFechaMascara( fecha, "dd/MM/yyyy")){
			Long folio = Folio.generaFolio();
			if( folio != 0 ){
				vale.setFolio(folio);
				valeDao.guardaVale(vale);
				resp = true;
			}
		}
		return resp;
	}
	
	public List<Vale> listarVales( String input ){
		UsuarioController usuarioController = new UsuarioController();
		Usuario usuario = null;
		if( input != null && ValidacionUtil.validarRut(input) ){
			usuario = usuarioController.obtenUsuarioRut(input);
		}else{
			usuario = usuarioController.obtenUsuario(input);
		}
		if( usuario == null )
			return null;
		else
			return usuario.getVales();
	}
	
	public List<Vale> listarVales( Date desde, Date hasta ){
		return valeDao.obtenListaVales(desde, hasta);
	}
	
	public List<Vale> listarVales( Estado estado ){
		return valeDao.obtenListaVales(estado);
	}
	
	public List<Vale> listarVales( Long idCentroCosto ){
		CentroCostoController ccController = new CentroCostoController();
		CentroCosto cc = ccController.obtenCentroCosto(idCentroCosto);
		
		UsuarioController uc = new UsuarioController();
		if ( cc != null ){
			List<Vale> vales = new ArrayList<Vale>();
			for( Usuario u : cc.getUsuarios() ){
				Usuario buff = uc.obtenUsuario(u.getIdusuario());
				for( Vale v : buff.getVales() ){
					vales.add(v);
				}
			}
			return vales;
		}else{
			return null;
		}
	}

	public List<Vale> listarValesUsuario( Estado estado , long idUsuario){
		return valeDao.obtenListaVales(estado, idUsuario);
	}
	public List<Vale> listarValesUsuario( Date desde, Date hasta ,long idUsuario ){
		return valeDao.obtenListaVales(desde, hasta, idUsuario);
	}
	public List<Vale> listarValesUsuario( long folio, long idUsuario){
		return valeDao.obtenListaVales(folio, idUsuario);
	}
	
	public boolean autorizarVale(Vale vale, Estado estado) {
		boolean confirmado = false;
		vale.setEstado(estado);
		if ( valeDao.actualizaVale(vale) )
			confirmado = true;
		return confirmado;
	}
	
	public boolean actualizarVale(Vale vale, int monto_real) {
		boolean confirmado = false;
		vale.setMonto_real(monto_real);
		if ( valeDao.actualizaVale(vale) )
			confirmado = true;
		return confirmado;
	}
	public boolean actualizarVale(Vale vale, Date date) {
		boolean confirmado = false;
		vale.setFecha_confirmacion(date);
		if ( valeDao.actualizaVale(vale) )
			confirmado = true;
		return confirmado;
	}
	
	public Vale obtenerVale(long id){
		return valeDao.obtenVale(id);
	}
	
	public Vale obtenerValeFolio(Long folio){
		return valeDao.obtenValeFolio(folio);
	}
}
