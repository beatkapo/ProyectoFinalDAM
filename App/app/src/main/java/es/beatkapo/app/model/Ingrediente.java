package es.beatkapo.app.model;

import java.util.List;

/**
 * Clase que representa un ingrediente.
 */
public class Ingrediente {
    private String id;
    private String nombre;
    private String descripcion;
    private List<Alergeno> alergenos;

    /**
     * Constructor con parámetros para inicializar un objeto Ingrediente.
     * @param id Identificador del ingrediente.
     * @param nombre Nombre del ingrediente.
     * @param descripcion Descripción del ingrediente.
     * @param alergenos Lista de alérgenos asociados al ingrediente.
     */
    public Ingrediente(String id, String nombre, String descripcion, List<Alergeno> alergenos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.alergenos = alergenos;
    }

    /**
     * Constructor vacío para inicializar un objeto Ingrediente sin parámetros.
     */
    public Ingrediente() {
    }

    /**
     * Obtiene el identificador del ingrediente.
     * @return Identificador del ingrediente.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador del ingrediente.
     * @param id Identificador del ingrediente.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del ingrediente.
     * @return Nombre del ingrediente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del ingrediente.
     * @param nombre Nombre del ingrediente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del ingrediente.
     * @return Descripción del ingrediente.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del ingrediente.
     * @param descripcion Descripción del ingrediente.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la lista de alérgenos asociados al ingrediente.
     * @return Lista de alérgenos.
     */
    public List<Alergeno> getAlergenos() {
        return alergenos;
    }

    /**
     * Establece la lista de alérgenos asociados al ingrediente.
     * @param alergenos Lista de alérgenos.
     */
    public void setAlergenos(List<Alergeno> alergenos) {
        this.alergenos = alergenos;
    }
}
