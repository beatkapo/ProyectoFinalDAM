package es.beatkapo.app.model;

import java.util.List;

public class Ingrediente {
    private String id;
    private String nombre;
    private String descripcion;
    private List<Alergeno> alergenos;

    public Ingrediente(String id, String nombre, String descripcion, List<Alergeno> alergenos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.alergenos = alergenos;
    }

    public Ingrediente() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Alergeno> getAlergenos() {
        return alergenos;
    }

    public void setAlergenos(List<Alergeno> alergenos) {
        this.alergenos = alergenos;
    }
}
