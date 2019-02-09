package Controlador;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import Modelo.Usuarios;
import Negocio.UsuariosDAO;

@ManagedBean
@ViewScoped
public class ControladorListaVendedores {

	@Inject
	UsuariosDAO udao;
	
	private List<Usuarios> lUsuarios = new ArrayList<>();
	
	@PostConstruct
	public void init(){
		cargarUsuarios();
	}
	
	public void cargarUsuarios() {
		lUsuarios = udao.listarUsuarios();
	}

	public List<Usuarios> getlUsuarios() {
		return lUsuarios;
	}

	public void setlUsuarios(List<Usuarios> lUsuarios) {
		this.lUsuarios = lUsuarios;
	}
	
}
