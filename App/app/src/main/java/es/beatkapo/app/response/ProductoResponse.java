package es.beatkapo.app.response;

import es.beatkapo.app.model.Producto;

/**
 * Clase que representa la respuesta de una solicitud de producto.
 */
public class ProductoResponse extends Response {
    private Producto producto; // Producto recibido en la respuesta

    /**
     * Constructor de la clase ProductoResponse.
     *
     * @param error     Indica si hubo un error en la respuesta.
     * @param errorCode Código de error, si lo hay.
     * @param message   Mensaje asociado a la respuesta.
     * @param producto  Producto recibido en la respuesta.
     */
    public ProductoResponse(boolean error, int errorCode, String message, Producto producto) {
        super(error, errorCode, message);
        this.producto = producto;
    }

    /**
     * Constructor vacío para inicializar un objeto ProductoResponse sin parámetros.
     */
    public ProductoResponse() {
    }

    /**
     * Obtiene el producto recibido en la respuesta.
     *
     * @return Producto.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Establece el producto recibido en la respuesta.
     *
     * @param producto Producto.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
