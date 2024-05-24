package es.beatkapo.app.response;

import java.util.List;

import es.beatkapo.app.model.Producto;

/**
 * Clase que representa la respuesta de una solicitud de productos.
 */
public class ProductosResponse extends Response {
    private List<Producto> productos; // Lista de productos recibidos en la respuesta

    /**
     * Constructor de la clase ProductosResponse.
     *
     * @param error     Indica si hubo un error en la respuesta.
     * @param errorCode Código de error, si lo hay.
     * @param message   Mensaje asociado a la respuesta.
     * @param productos Lista de productos recibidos en la respuesta.
     */
    public ProductosResponse(boolean error, int errorCode, String message, List<Producto> productos) {
        super(error, errorCode, message);
        this.productos = productos;
    }

    /**
     * Constructor vacío para inicializar un objeto ProductosResponse sin parámetros.
     */
    public ProductosResponse() {
    }

    /**
     * Obtiene la lista de productos recibidos en la respuesta.
     *
     * @return Lista de productos.
     */
    public List<Producto> getProductos() {
        return productos;
    }

    /**
     * Establece la lista de productos recibidos en la respuesta.
     *
     * @param productos Lista de productos.
     */
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
