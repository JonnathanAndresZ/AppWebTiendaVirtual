package Negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import Modelo.Productos;

@Stateless
public class ProductosDAO {
	
	@Inject
	private EntityManager em;
	
	public void registraProducto(Productos p) {
		em.persist(p);
	}
	
	public void eliminaProducto(Productos p) {
		em.remove(em.contains(p) ? p : em.merge(p));
	}
	
	public void editarProducto(Productos p) {
		em.merge(p);
	}
	
	public Productos buscaIdProducto(int id){
		Productos producto = new Productos();
		Query buscaProducto = em.createNamedQuery("Productos.findById");
		buscaProducto.setParameter("id", id);
		producto = (Productos) buscaProducto.getSingleResult();
		return producto;
	}
	
	public List<Productos> listaProductos(){
		List<Productos> lProductos = new ArrayList<>();
		Query listaProductos = em.createNamedQuery("Productos.findAll");
		lProductos = (List<Productos>) listaProductos.getResultList();
		return lProductos;
	}

}
