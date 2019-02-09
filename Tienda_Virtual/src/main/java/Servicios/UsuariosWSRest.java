package Servicios;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import Modelo.Usuarios;
import ModeloWs.UsuariosWs;
import Negocio.ProductosDAO;
import Negocio.UsuariosDAO;

@Path("usuarios")
public class UsuariosWSRest {
	
	@EJB
	UsuariosDAO uDAO = new UsuariosDAO();
	
	@GET
	@Path("login")
	@Produces ("application/json")
	public UsuariosWs filtroAlumnosCorreo(@QueryParam("correo") String correo, @QueryParam("passwd") String passwd)
	{
		UsuariosWs usuWs = new UsuariosWs();
		Usuarios usuario = new Usuarios();
		try {
			usuario = uDAO.login(correo, passwd);
			if (!usuario.equals(null)) {
				usuWs.setId(usuario.getId());
				usuWs.setCedula(usuario.getCedula());
				usuWs.setNombre(usuario.getNombre());
				usuWs.setApellido(usuario.getApellido());
				usuWs.setCorreo(usuario.getCorreo());
				usuWs.setRol(usuario.getRol());
				usuWs.setContrasena(usuario.getContrasena());
				return usuWs;
			} else {
				return usuWs;
			}
		} catch (Exception e){
			return usuWs;
		}
	}

}
