package es.beatkapo.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un pedido.
 */
public class Pedido {
    private String id;
    private String fecha;
    private String estado;
    private Usuario usuario;
    private String direccion;
    private List<LineaPedido> lineas;
    private double total;

    /**
     * Constructor con parámetros para inicializar un objeto Pedido.
     * @param id Identificador del pedido.
     * @param fecha Fecha del pedido.
     * @param estado Estado del pedido.
     * @param usuario Usuario que realiza el pedido.
     * @param lineas Lista de líneas de pedido.
     * @param total Total del pedido.
     */
    public Pedido(String id, String fecha, String estado, Usuario usuario, List<LineaPedido> lineas, double total) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.usuario = usuario;
        this.lineas = lineas;
        this.total = total;
    }

    /**
     * Constructor vacío para inicializar un objeto Pedido sin parámetros.
     */
    public Pedido() {
    }

    /**
     * Obtiene la dirección del pedido.
     * @return Dirección del pedido.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del pedido.
     * @param direccion Dirección del pedido.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el identificador del pedido.
     * @return Identificador del pedido.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador del pedido.
     * @param id Identificador del pedido.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene la fecha del pedido.
     * @return Fecha del pedido.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del pedido.
     * @param fecha Fecha del pedido.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el estado del pedido.
     * @return Estado del pedido.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del pedido.
     * @param estado Estado del pedido.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el usuario que realiza el pedido.
     * @return Usuario que realiza el pedido.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario que realiza el pedido.
     * @param usuario Usuario que realiza el pedido.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la lista de líneas de pedido.
     * @return Lista de líneas de pedido.
     */
    public List<LineaPedido> getLineas() {
        return lineas;
    }

    /**
     * Establece la lista de líneas de pedido.
     * @param lineas Lista de líneas de pedido.
     */
    public void setLineas(List<LineaPedido> lineas) {
        this.lineas = lineas;
    }

    /**
     * Calcula el total del pedido sumando los totales de cada línea de pedido.
     * @return Total del pedido.
     */
    public double getTotal() {
        double auxTotal = 0;
        for (LineaPedido linea : lineas) {
            auxTotal += linea.getTotal();
        }
        return auxTotal;
    }

    /**
     * Obtiene la cantidad total de productos en el pedido.
     * @return Cantidad total de productos.
     */
    public int getCantidadProductos() {
        if (lineas == null) {
            return 0;
        }
        int cantidad = 0;
        for (LineaPedido linea : lineas) {
            cantidad += linea.getCantidad();
        }
        return cantidad;
    }

    /**
     * Establece el total del pedido.
     * @param total Total del pedido.
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Añade un producto al pedido con la cantidad especificada.
     * @param producto Producto a añadir.
     * @param cantidad Cantidad del producto a añadir.
     */
    public void addProducto(Producto producto, int cantidad) {
        LineaPedido linea = new LineaPedido(producto, cantidad);
        if (lineas == null) {
            lineas = new ArrayList<>();
        }
        lineas.add(linea);
    }

    /**
     * Elimina una línea de pedido del pedido.
     * @param linea Línea de pedido a eliminar.
     */
    public void removeLinea(LineaPedido linea) {
        lineas.remove(linea);
    }
}
