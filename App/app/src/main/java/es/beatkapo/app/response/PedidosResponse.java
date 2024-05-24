package es.beatkapo.app.response;

import java.util.List;

import es.beatkapo.app.model.Pedido;

/**
 * Clase que representa la respuesta de una solicitud de pedidos.
 */
public class PedidosResponse extends Response {
    private List<Pedido> pedidos; // Lista de pedidos recibidos en la respuesta

    /**
     * Constructor de la clase PedidosResponse.
     *
     * @param error     Indica si hubo un error en la respuesta.
     * @param errorCode Código de error, si lo hay.
     * @param message   Mensaje asociado a la respuesta.
     * @param pedidos   Lista de pedidos recibidos en la respuesta.
     */
    public PedidosResponse(boolean error, int errorCode, String message, List<Pedido> pedidos) {
        super(error, errorCode, message);
        this.pedidos = pedidos;
    }

    /**
     * Obtiene la lista de pedidos recibidos en la respuesta.
     *
     * @return Lista de pedidos.
     */
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    /**
     * Establece la lista de pedidos recibidos en la respuesta.
     *
     * @param pedidos Lista de pedidos.
     */
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
