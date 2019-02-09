package Modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")
    , @NamedQuery(name = "Usuarios.findById", query = "SELECT u FROM Usuarios u WHERE u.id = :id")
    , @NamedQuery(name = "Usuarios.login", query = "SELECT u FROM Usuarios u WHERE u.correo = :correo AND u.contrasena = :password")})
public class Usuarios {
	
	private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "usr_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Integer id;
    
    @Size(max = 10)
    @Column(name = "usr_cedula", length = 10)
    @NotBlank(message = "Porfavor ingrece la cédula.")
    private String cedula;
    
    @Size(min = 2, max = 45)
	@NotBlank(message = "Porfavor ingrece el nombre.")
    @Column(name = "usr_nombre", length = 45)
    private String nombre;
    
    @Size(min = 2, max = 45)
	@NotBlank(message = "Porfavor ingrese el apellido.")
    @Column(name = "usr_apellido", length = 45)
    private String apellido;
    
	@NotNull
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "El correo ingresado es incorrecto.")
    @Column(name = "usr_correo", length = 45)
    private String correo;
    
    @Size(max = 45)
    @Column(name = "usr_rol", length = 45)
    private String rol;
    
    @Size(min = 2, max = 100)
	@NotBlank(message = "Porfavor ingrese una contraseña válida.")
    @Column(name = "usr_contrasena", length = 100)
    private String contrasena;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuarios")
    private List<Productos> productosList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public List<Productos> getProductosList() {
		return productosList;
	}

	public void setProductosList(List<Productos> productosList) {
		this.productosList = productosList;
	}

}
