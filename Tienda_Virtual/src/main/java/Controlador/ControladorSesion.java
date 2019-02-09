package Controlador;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import Modelo.Usuarios;
import Negocio.UsuariosDAO;
import Utilidades.SessionUtils;

@ManagedBean
@ViewScoped
public class ControladorSesion {
	
	@Inject
	UsuariosDAO udao;
	
	@NotBlank(message = "Porfavor ingrese el correo.")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Porfavor ingrese un correo válido.")
	private String correo;
	
	@NotBlank(message = "Porfavor ingrese la contraseña.")
	private String password;
	
	private Usuarios usuario = new Usuarios();
	
	private String incorrecto = "";
	
	private String iniciarSesion;
	private String miCuenta;
	private String cerrarSesion;
	
	public void login() {
		
		HttpSession session = SessionUtils.getSession();
		session.setAttribute("rol", "SR");
		if (getCorreo().equals("admin@hotmail.com") && getPassword().equals("admin123")) {
			
			session.setAttribute("id", 0);
			session.setAttribute("nombre", "Admin");
			session.setAttribute("apellido", "Admin");
			session.setAttribute("correo", "admin@hotmail.com");
			session.setAttribute("rol", "Administrador");
			setIncorrecto("");
		} else {
			
			try {
				usuario = udao.login(getCorreo(), getPassword());
				session.setAttribute("id", usuario.getId());
				session.setAttribute("nombre", usuario.getNombre());
				session.setAttribute("apellido", usuario.getApellido());
				session.setAttribute("correo", usuario.getCorreo());
				session.setAttribute("rol", usuario.getRol());
				setIncorrecto("");
			} catch (Exception e) {
				System.out.println("Usuario o contraseña incorrecto");
				setIncorrecto("Usuario o contraseña incorrecto");
			}
			
		}
		
		try {
			if (session.getAttribute("rol").equals("Administrador")){
				FacesContext contex = FacesContext.getCurrentInstance();
				contex.getExternalContext().redirect( "vendedoresAdmin.jsf" );
			} else {
				if (session.getAttribute("rol").equals("Vendedor")){
					FacesContext contex = FacesContext.getCurrentInstance();
					contex.getExternalContext().redirect( "productosVendedor.jsf" );
				}
			}
		} catch (IOException e) {
			System.out.println("No se a iniciado sesión");
		}
		
	}
	
	public void verificaSesionIndex(){
		 HttpSession session = SessionUtils.getSession();
			String nusv = (String) session.getAttribute("nombre");
				if(nusv!=null){
					iniciarSesion = "none";
					miCuenta = "display";
					cerrarSesion = "display";
				} else {
					iniciarSesion = "display";
					miCuenta = "none";
					cerrarSesion = "none";
				}
	}
	
	public void cerrarSesion() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		FacesContext contex = FacesContext.getCurrentInstance();
        try {
			contex.getExternalContext().redirect( "index.jsf" );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void linkMiCuenta(){
		HttpSession session = SessionUtils.getSession();
		String rol = (String) session.getAttribute("rol");
		if (rol != null){
			if(rol.equals("Administrador")){
				FacesContext contex = FacesContext.getCurrentInstance();
			    try {
					contex.getExternalContext().redirect( "vendedoresAdmin.jsf" );
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				FacesContext contex = FacesContext.getCurrentInstance();
			    try {
					contex.getExternalContext().redirect( "productosVendedor.jsf" );
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			FacesContext contex = FacesContext.getCurrentInstance();
		    try {
				contex.getExternalContext().redirect( "login.jsf" );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void verificaSesionAdmin(){
		HttpSession session = SessionUtils.getSession();
		String rol = (String) session.getAttribute("rol");
		if (rol != null){
			if(rol.equals("Administrador")){
			} else {
				FacesContext contex = FacesContext.getCurrentInstance();
			    try {
					contex.getExternalContext().redirect( "index.jsf" );
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			FacesContext contex = FacesContext.getCurrentInstance();
		    try {
				contex.getExternalContext().redirect( "index.jsf" );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void verificaSesionVendedor(){
		HttpSession session = SessionUtils.getSession();
		String rol = (String) session.getAttribute("rol");
		if (rol != null){
			if(rol.equals("Vendedor")){
			} else {
				FacesContext contex = FacesContext.getCurrentInstance();
			    try {
					contex.getExternalContext().redirect( "index.jsf" );
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			FacesContext contex = FacesContext.getCurrentInstance();
		    try {
				contex.getExternalContext().redirect( "index.jsf" );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getIncorrecto() {
		return incorrecto;
	}

	public void setIncorrecto(String incorrecto) {
		this.incorrecto = incorrecto;
	}

	public String getIniciarSesion() {
		return iniciarSesion;
	}

	public void setIniciarSesion(String iniciarSesion) {
		this.iniciarSesion = iniciarSesion;
	}

	public String getMiCuenta() {
		return miCuenta;
	}

	public void setMiCuenta(String miCuenta) {
		this.miCuenta = miCuenta;
	}

	public String getCerrarSesion() {
		return cerrarSesion;
	}

	public void setCerrarSesion(String cerrarSesion) {
		this.cerrarSesion = cerrarSesion;
	}

}
