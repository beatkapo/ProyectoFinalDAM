package es.beatkapo.app.model;

import java.util.List;

public class Producto {
    private String id;
    private String nombre;
    private String descripcion;
    private String descripcionCorta;
    private String imagen;
    private float precio;
    private List<Ingrediente> ingredientes;
    private Categoria categoria;

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

    public Producto() {
    }

    public void addIngrediente(Ingrediente ingrediente){
        ingredientes.add(ingrediente);
    }
    public void removeIngrediente(Ingrediente ingrediente){
        ingredientes.remove(ingrediente);
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

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
