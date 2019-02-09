package Controlador;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.hibernate.validator.constraints.NotBlank;

import Modelo.Usuarios;
import Negocio.UsuariosDAO;

@ManagedBean
@ViewScoped
public class ControladorUsuarios {
	
	@Inject
	UsuariosDAO udao;
	
	private Usuarios usuario = new Usuarios();
	
	private String password1Edita = "";
	private String password2Edita = "";
	
	@NotBlank(message = "Porfavor repita la contraseña.")
	private String password;
	
	private String msjPassword = "";
	
	private String msjEditarUsuario = "";
	
	private int idUsuario;
	
	@PostConstruct
	public void init() {
		try {
			FacesContext facesContext = FacesContext. getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			Map params = externalContext.getRequestParameterMap();
			idUsuario = new Integer((String) params.get("usuario" ));
			usuario = udao.buscaIdUsuario(idUsuario);
		} catch (Exception e) {
			System.out.println("No tiene parámetro en el URL por que es un nuevo usuario");
		}
	}
	
	public void Guardar() {
		
		usuario.setRol("Vendedor");
		if (usuario.getContrasena().equals(password)) {
			udao.registraUsuario(usuario);
			usuario = new Usuarios();
			setMsjPassword("");
		} else {
			setMsjPassword("Porfavor ingrese las mismas contraseñas");
		}
		
	}

	public void eliminaUsuario(int id) {
		Usuarios usuario = new Usuarios();
		usuario = udao.buscaIdUsuario(id);
		udao.eliminaUsuario(usuario);
		try {
			FacesContext contex = FacesContext.getCurrentInstance();
			contex.getExternalContext().redirect( "vendedoresAdmin.jsf" );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editarUsuario() {
		if (password1Edita.equals("") && password2Edita.equals("")) {
			udao.editarUsuario(usuario);
			setMsjPassword("");
			setMsjEditarUsuario("Se ha editado exitosamente");
		} else {
			if (password1Edita.equals(password2Edita)) {
				usuario.setContrasena(password1Edita);
				udao.editarUsuario(usuario);
				setMsjPassword("");
				setMsjEditarUsuario("Se ha editado exitosamente");
			} else {
				setMsjPassword("Porfavor ingrese las mismas contraseñas");
			}
		}
	}
	
	public String getMsjPassword() {
		return msjPassword;
	}

	public void setMsjPassword(String msjPassword) {
		this.msjPassword = msjPassword;
	}

	public String getPassword1Edita() {
		return password1Edita;
	}

	public void setPassword1Edita(String password1Edita) {
		this.password1Edita = password1Edita;
	}

	public String getPassword2Edita() {
		return password2Edita;
	}

	public void setPassword2Edita(String password2Edita) {
		this.password2Edita = password2Edita;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMsjEditarUsuario() {
		return msjEditarUsuario;
	}

	public void setMsjEditarUsuario(String msjEditarUsuario) {
		this.msjEditarUsuario = msjEditarUsuario;
	}

}
