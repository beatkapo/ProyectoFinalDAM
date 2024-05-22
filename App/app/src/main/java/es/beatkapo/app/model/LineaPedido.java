package es.beatkapo.app.model;

public class LineaPedido {
    private String id;
    private int cantidad;
    private Producto producto;

    public LineaPedido(String id, int cantidad, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.producto = producto;
    }


    public LineaPedido() {
    }

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public double getTotal(){
        return cantidad*producto.getPrecio();
    }

    public float getPrecio() {
        return producto.getPrecio()*cantidad;
    }
}
