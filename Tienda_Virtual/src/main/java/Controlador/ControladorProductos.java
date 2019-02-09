package Controlador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import Modelo.Productos;
import Modelo.Usuarios;
import Negocio.ProductosDAO;
import Negocio.UsuariosDAO;
import Utilidades.SessionUtils;

@ManagedBean
@ViewScoped
public class ControladorProductos {
	
	@Inject
	private ProductosDAO pdao;
	
	@Inject
	private UsuariosDAO udao;
	
	private Productos producto = new Productos();
	
	private Part uploadedFile1;
	private Part uploadedFile2;
	private Part uploadedFile3;
	
	private String msjEditarProducto = "";
	
	int idProducto;
	
	@PostConstruct
	public void init() {
		try {
			FacesContext facesContext = FacesContext. getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			Map params = externalContext.getRequestParameterMap();
			idProducto = new Integer((String) params.get("producto" ));
			producto = pdao.buscaIdProducto(idProducto);
		} catch (Exception e) {
			System.out.println("No tiene parámetro en el URL por que es un nuevo producto");
		}
	}
	
	public static String getPath() {
		try {
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	        return ctx.getRealPath("/");
        } catch (Exception e) {
        	System.out.println(e);
        	return null;
	    }
    }
	
	public String saveFile(Part uploadedFile){
		String ubArchivo = "";
	    try (InputStream input = uploadedFile.getInputStream()) {
	    	String tipoArchivo = uploadedFile.getContentType();
	    	String[] extensiones = tipoArchivo.split("/");
	    	String extension = extensiones[1];
	    	java.util.Date fecha = new Date();
			String fechaAct = String.valueOf(fecha);
			String fAct = fechaAct.replaceAll(" ","");
	    	int x = (int)(Math.random()*(999-1+1)+1);
        	String nombreArchivo= String.valueOf(x) + fAct.replaceAll(":", "") + "." + extension;
        	System.out.println(nombreArchivo);
        	ubArchivo = "/resources/images/productos/"+nombreArchivo;
	        Files.copy(input, new File(getPath()+"/resources/images/productos/", nombreArchivo).toPath());
	        return ubArchivo;
	    } catch (IOException e) {
	    	System.out.println("Sin foto");
	    	return ubArchivo;
	    }
	}
	
	public void Guardar() {
		
		Usuarios usuario = new Usuarios();
			
		HttpSession session = SessionUtils.getSession();
		
		usuario = udao.buscaIdUsuario(Integer.parseInt(session.getAttribute("id").toString()));
		
		try {		
			producto.setFoto1(saveFile(uploadedFile1));
		} catch (Exception e) {
			System.out.println("No se subieron las imagenes");
		}
		try {
			producto.setFoto2(saveFile(uploadedFile2));
		} catch (Exception e) {
			System.out.println("No se subieron las imagenes");
		}
		try {
			producto.setFoto3(saveFile(uploadedFile3));
		} catch (Exception e) {
			System.out.println("No se subieron las imagenes");
		}
		producto.setUsuarios(usuario);
		pdao.registraProducto(producto);
		producto = new Productos();
		
	}

	public void eliminaProducto(int id) {
		Productos producto = new Productos();
		producto = pdao.buscaIdProducto(id);
		pdao.eliminaProducto(producto);
		try {
			FacesContext contex = FacesContext.getCurrentInstance();
			contex.getExternalContext().redirect( "productosVendedor.jsf" );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editarProducto() {
		try {		
			producto.setFoto1(saveFile(uploadedFile1));
		} catch (Exception e) {
			System.out.println("No se subieron las imagenes");
		}
		try {
			producto.setFoto2(saveFile(uploadedFile2));
		} catch (Exception e) {
			System.out.println("No se subieron las imagenes");
		}
		try {
			producto.setFoto3(saveFile(uploadedFile3));
		} catch (Exception e) {
			System.out.println("No se subieron las imagenes");
		}
		pdao.editarProducto(producto);
		setMsjEditarProducto("Producto editado exitosamente");
		
	}
	
	public Productos getProducto() {
		return producto;
	}

	public void setProducto(Productos producto) {
		this.producto = producto;
	}

	public Part getUploadedFile1() {
		return uploadedFile1;
	}

	public void setUploadedFile1(Part uploadedFile1) {
		this.uploadedFile1 = uploadedFile1;
	}

	public Part getUploadedFile2() {
		return uploadedFile2;
	}

	public void setUploadedFile2(Part uploadedFile2) {
		this.uploadedFile2 = uploadedFile2;
	}

	public Part getUploadedFile3() {
		return uploadedFile3;
	}

	public void setUploadedFile3(Part uploadedFile3) {
		this.uploadedFile3 = uploadedFile3;
	}

	public String getMsjEditarProducto() {
		return msjEditarProducto;
	}

	public void setMsjEditarProducto(String msjEditarProducto) {
		this.msjEditarProducto = msjEditarProducto;
	}

}
