package es.beatkapo.app.model;

/**
 * Clase que representa un alérgeno.
 */
public class Alergeno {
    private String id;
    private String nombre;
    private String descripcion;

    /**
     * Constructor con parámetros para inicializar un objeto Alérgeno.
     * @param id Identificador del alérgeno.
     * @param nombre Nombre del alérgeno.
     * @param descripcion Descripción del alérgeno.
     */
    public Alergeno(String id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * Constructor vacío para inicializar un objeto Alérgeno sin parámetros.
     */
    public Alergeno() {
    }

    /**
     * Obtiene el identificador del alérgeno.
     * @return Identificador del alérgeno.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador del alérgeno.
     * @param id Identificador del alérgeno.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del alérgeno.
     * @return Nombre del alérgeno.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del alérgeno.
     * @param nombre Nombre del alérgeno.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del alérgeno.
     * @return Descripción del alérgeno.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del alérgeno.
     * @param descripcion Descripción del alérgeno.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
