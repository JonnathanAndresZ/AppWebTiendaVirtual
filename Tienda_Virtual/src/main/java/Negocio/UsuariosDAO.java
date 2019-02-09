package Negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import Modelo.Usuarios;

@Stateless
public class UsuariosDAO {
	
	@Inject
	private EntityManager em;
	
	public void registraUsuario(Usuarios u) {
		em.persist(u);
	}
	
	public void eliminaUsuario(Usuarios u) {
		em.remove(em.contains(u) ? u : em.merge(u));
	}
	
	public void editarUsuario(Usuarios u) {
		em.merge(u);
	}
	
	public Usuarios buscaIdUsuario(int id){
		Usuarios usuario = new Usuarios();
		Query buscaUsuario = em.createNamedQuery("Usuarios.findById");
		buscaUsuario.setParameter("id", id);
		usuario = (Usuarios) buscaUsuario.getSingleResult();
		usuario.getProductosList().size();
		return usuario;
	}
	
	public Usuarios login(String correo, String contrasena) {
		try {
			Usuarios usuario = new Usuarios();
			Query loginUsuario = em.createNamedQuery("Usuarios.login");
			loginUsuario.setParameter("correo", correo);
			loginUsuario.setParameter("password", contrasena);
			usuario = (Usuarios) loginUsuario.getSingleResult();
			usuario.getProductosList().size();
			return usuario;
		} catch (Exception e) {
			System.out.println("No hay coincidencias de correo y contraseña");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuarios> listarUsuarios() {
		List<Usuarios> lUsuarios = new ArrayList<>();
		Query listaTodosUsuarios = em.createNamedQuery("Usuarios.findAll");
		lUsuarios = (List<Usuarios>) listaTodosUsuarios.getResultList();
		return lUsuarios;
	}

}
