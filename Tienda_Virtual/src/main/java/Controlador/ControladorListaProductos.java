package Controlador;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import Modelo.Productos;
import Modelo.Usuarios;
import Negocio.ProductosDAO;
import Negocio.UsuariosDAO;
import Utilidades.SessionUtils;

@ManagedBean
@ViewScoped
public class ControladorListaProductos {

	@Inject
	UsuariosDAO udao;
	
	@Inject
	ProductosDAO pdao;
	
	private Usuarios usuario = new Usuarios();
	
	private List<Productos> lProductos = new ArrayList<>();
	
	private List<Productos> lCompletaProductos = new ArrayList<>();
	
	@PostConstruct
	public void init(){
		listaCompleta();
		try {
			cargarProductos();
		} catch (Exception e) {
			listaCompleta();
		}
	}
	
	public void cargarProductos() {
		HttpSession session = SessionUtils.getSession();
		usuario = udao.buscaIdUsuario(Integer.parseInt(session.getAttribute("id").toString()));
		lProductos = usuario.getProductosList();
	}
	
	public void listaCompleta() {
		lCompletaProductos = pdao.listaProductos();
	}

	public List<Productos> getlProductos() {
		return lProductos;
	}

	public void setlProductos(List<Productos> lProductos) {
		this.lProductos = lProductos;
	}

	public List<Productos> getlCompletaProductos() {
		return lCompletaProductos;
	}

	public void setlCompletaProductos(List<Productos> lCompletaProductos) {
		this.lCompletaProductos = lCompletaProductos;
	}
	
}
