package es.beatkapo.app.service;

import es.beatkapo.app.model.LineaPedido;
import es.beatkapo.app.model.Pedido;
import es.beatkapo.app.response.Response;
import es.beatkapo.app.util.NodeServer;

/**
 * Clase para enviar un pedido al servidor.
 */
public class SendOrder extends BaseService<Pedido, Response> {

    /**
     * Realiza la solicitud para enviar un pedido al servidor.
     *
     * @param pedido     Objeto Pedido que representa el pedido a enviar.
     * @param onSuccess  Callback llamado cuando la solicitud se completa con éxito.
     * @param onFailure  Callback llamado cuando la solicitud falla.
     */
    public void sendOrder(Pedido pedido, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {

        // Calcula el total del pedido sumando el total de cada línea de pedido.
        for (LineaPedido lineaPedido : pedido.getLineas()) {
            float totalLinea = lineaPedido.getCantidad() * lineaPedido.getPrecio();
            pedido.setTotal(pedido.getTotal() + totalLinea);
        }

        String url = NodeServer.getServer() + "/api/saveOrder"; // URL para la solicitud de guardar el pedido.
        executeRequest(url, pedido, "POST", onSuccess, onFailure); // Ejecuta la solicitud HTTP POST.
    }

    /**
     * Obtiene la clase de respuesta asociada al servicio.
     *
     * @return Clase de respuesta Response.
     */
    @Override
    protected Class<Response> getResponseClass() {
        return Response.class;
    }
}
