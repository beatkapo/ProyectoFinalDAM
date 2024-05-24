package es.beatkapo.app.model;

/**
 * Clase que representa una línea de pedido.
 */
public class LineaPedido {
    private String id;
    private int cantidad;
    private Producto producto;

    /**
     * Constructor con parámetros para inicializar un objeto LineaPedido.
     * @param id Identificador de la línea de pedido.
     * @param cantidad Cantidad de productos en la línea de pedido.
     * @param producto Producto asociado a la línea de pedido.
     */
    public LineaPedido(String id, int cantidad, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.producto = producto;
    }

    /**
     * Constructor vacío para inicializar un objeto LineaPedido sin parámetros.
     */
    public LineaPedido() {
    }

    /**
     * Constructor para inicializar un objeto LineaPedido con un producto y cantidad.
     * @param producto Producto asociado a la línea de pedido.
     * @param cantidad Cantidad de productos en la línea de pedido.
     */
    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el identificador de la línea de pedido.
     * @return Identificador de la línea de pedido.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador de la línea de pedido.
     * @param id Identificador de la línea de pedido.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene la cantidad de productos en la línea de pedido.
     * @return Cantidad de productos.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de productos en la línea de pedido.
     * @param cantidad Cantidad de productos.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el producto asociado a la línea de pedido.
     * @return Producto.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Establece el producto asociado a la línea de pedido.
     * @param producto Producto.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Calcula el total de la línea de pedido.
     * @return Total de la línea de pedido.
     */
    public double getTotal() {
        return cantidad * producto.getPrecio();
    }

    /**
     * Calcula el precio total basado en la cantidad de productos.
     * @return Precio total.
     */
    public float getPrecio() {
        return producto.getPrecio() * cantidad;
    }
}
