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

import Modelo.Productos;
import Modelo.Usuarios;
import ModeloWs.ProductosWs;
import Negocio.ProductosDAO;
import Negocio.UsuariosDAO;

@Path("productos")
public class ProductosWSRest {
	
	@EJB
	ProductosDAO pDAO = new ProductosDAO();
	
	@EJB
	UsuariosDAO uDAO = new UsuariosDAO();
	
	Productos productos = new Productos();
	List<Productos> lProductos = new ArrayList<>();
	List<ProductosWs> lProductosWs = new ArrayList<>();
	ProductosWs productosWs;
	
	@GET
	@Path("listado")
	@Produces ("application/json")
	public List<ProductosWs> listadoProductos()
	{
		try {
			lProductos = pDAO.listaProductos();
			for (int i=0; i < lProductos.size() - 1; i++){
				productosWs = new ProductosWs();
				productosWs.setId(lProductos.get(i).getId());
				productosWs.setNombre(lProductos.get(i).getNombre());
				productosWs.setDescripcion(lProductos.get(i).getDescripcion());
				productosWs.setStock(lProductos.get(i).getStock());
				productosWs.setFoto1(lProductos.get(i).getFoto1());
				lProductosWs.add(productosWs);
			}
			return lProductosWs;
		} catch (Exception e){
			System.out.println("Error al recuperar productos");
			return null;
		}
	}
	
	@GET
	@Path("almacena")
	@Produces ("application/json")
	public Boolean guardaProductos(@QueryParam("idU") int idU, @QueryParam("nombre") String nombre, @QueryParam("descripcion") String descripcion, @QueryParam("stock") int stock)
	{
		try {
			List<Usuarios> lUsuarios = new ArrayList<>();
			Usuarios usuario = new Usuarios();
			Productos producto = new Productos();
			lUsuarios = uDAO.listarUsuarios();
			for (int i=0; i <= lUsuarios.size()-1; i++){
				if (lUsuarios.get(i).getId() == idU){
					usuario = lUsuarios.get(i);
				}
			}
			producto.setNombre(nombre);
			producto.setDescripcion(descripcion);
			producto.setStock(stock);
			producto.setUsuarios(usuario);
			pDAO.registraProducto(producto);
			return true;
		} catch (Exception e){
			System.out.println("Error al guardar el producto");
			e.printStackTrace();
			return false;
		}
	}
	
	@GET
	@Path("elimina")
	@Produces ("application/json")
	public Boolean guardaProductos(@QueryParam("idP") int idP)
	{
		try {
			Productos p = new Productos();
			p = pDAO.buscaIdProducto(idP);
			pDAO.eliminaProducto(p);
			return true;
		} catch (Exception e){
			System.out.println("Error al eliminar el producto");
			e.printStackTrace();
			return false;
		}
	}
	
	@GET
	@Path("editar")
	@Produces ("application/json")
	public Boolean editarProductos(@QueryParam("idU") int idU, @QueryParam("idP") int idP, @QueryParam("nombre") String nombre, @QueryParam("descripcion") String descripcion, @QueryParam("stock") int stock)
	{
		try {
			List<Usuarios> lUsuarios = new ArrayList<>();
			Usuarios usuario = new Usuarios();
			Productos producto = new Productos();
			lUsuarios = uDAO.listarUsuarios();
			for (int i=0; i <= lUsuarios.size()-1; i++){
				if (lUsuarios.get(i).getId() == idU){
					usuario = lUsuarios.get(i);
				}
			}
			producto.setId(idP);
			producto.setNombre(nombre);
			producto.setDescripcion(descripcion);
			producto.setStock(stock);
			producto.setUsuarios(usuario);
			pDAO.editarProducto(producto);
			return true;
		} catch (Exception e){
			System.out.println("Error al guardar el producto");
			e.printStackTrace();
			return false;
		}
	}

}
