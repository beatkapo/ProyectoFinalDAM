package es.beatkapo.app.model;

/**
 * Clase que representa una categoría.
 */
public class Categoria {
    private String id;
    private String nombre;
    private String descripcion;

    /**
     * Constructor con parámetros para inicializar un objeto Categoria.
     * @param id Identificador de la categoría.
     * @param nombre Nombre de la categoría.
     * @param descripcion Descripción de la categoría.
     */
    public Categoria(String id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * Constructor vacío para inicializar un objeto Categoria sin parámetros.
     */
    public Categoria() {
    }

    /**
     * Obtiene el identificador de la categoría.
     * @return Identificador de la categoría.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador de la categoría.
     * @param id Identificador de la categoría.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la categoría.
     * @return Nombre de la categoría.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la categoría.
     * @param nombre Nombre de la categoría.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción de la categoría.
     * @return Descripción de la categoría.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la categoría.
     * @param descripcion Descripción de la categoría.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
