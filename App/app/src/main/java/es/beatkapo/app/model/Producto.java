package es.beatkapo.app.model;

import java.util.List;

/**
 * Clase que representa un producto.
 */
public class Producto {
    private String id;
    private String nombre;
    private String descripcion;
    private String descripcionCorta;
    private String imagen;
    private float precio;
    private List<Ingrediente> ingredientes;
    private Categoria categoria;

    /**
     * Constructor con parámetros para inicializar un objeto Producto.
     * @param id Identificador del producto.
     * @param nombre Nombre del producto.
     * @param descripcion Descripción detallada del producto.
     * @param descripcionCorta Descripción corta del producto.
     * @param imagen URL de la imagen del producto.
     * @param precio Precio del producto.
     * @param ingredientes Lista de ingredientes del producto.
     * @param categoria Categoría a la que pertenece el producto.
     */
    public Producto(String id, String nombre, String descripcion, String descripcionCorta, String imagen, float precio, List<Ingrediente> ingredientes, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.descripcionCorta = descripcionCorta;
        this.imagen = imagen;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.categoria = categoria;
    }

    /**
     * Constructor vacío para inicializar un objeto Producto sin parámetros.
     */
    public Producto() {
    }

    /**
     * Añade un ingrediente a la lista de ingredientes del producto.
     * @param ingrediente Ingrediente a añadir.
     */
    public void addIngrediente(Ingrediente ingrediente) {
        ingredientes.add(ingrediente);
    }

    /**
     * Elimina un ingrediente de la lista de ingredientes del producto.
     * @param ingrediente Ingrediente a eliminar.
     */
    public void removeIngrediente(Ingrediente ingrediente) {
        ingredientes.remove(ingrediente);
    }

    /**
     * Obtiene el identificador del producto.
     * @return Identificador del producto.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador del producto.
     * @param id Identificador del producto.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del producto.
     * @return Nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre Nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción detallada del producto.
     * @return Descripción detallada del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción detallada del producto.
     * @param descripcion Descripción detallada del producto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la descripción corta del producto.
     * @return Descripción corta del producto.
     */
    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    /**
     * Establece la descripción corta del producto.
     * @param descripcionCorta Descripción corta del producto.
     */
    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    /**
     * Obtiene la URL de la imagen del producto.
     * @return URL de la imagen del producto.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece la URL de la imagen del producto.
     * @param imagen URL de la imagen del producto.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene el precio del producto.
     * @return Precio del producto.
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     * @param precio Precio del producto.
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la lista de ingredientes del producto.
     * @return Lista de ingredientes del producto.
     */
    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    /**
     * Establece la lista de ingredientes del producto.
     * @param ingredientes Lista de ingredientes del producto.
     */
    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    /**
     * Obtiene la categoría del producto.
     * @return Categoría del producto.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del producto.
     * @param categoria Categoría del producto.
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
