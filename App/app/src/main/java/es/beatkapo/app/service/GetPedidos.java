package es.beatkapo.app.service;

import es.beatkapo.app.response.PedidosResponse;
import es.beatkapo.app.util.NodeServer;

/**
 * Clase para obtener la lista de pedidos desde el servidor.
 */
public class GetPedidos extends BaseService<Void, PedidosResponse> {

    /**
     * Obtiene la clase de respuesta asociada al servicio.
     *
     * @return Clase de respuesta PedidosResponse.
     */
    @Override
    protected Class<PedidosResponse> getResponseClass() {
        return PedidosResponse.class;
    }

    /**
     * Obtiene la lista de pedidos desde el servidor.
     *
     * @param onSuccess Callback llamado cuando la solicitud se completa con éxito.
     * @param onFailure Callback llamado cuando la solicitud falla.
     */
    public void getProductos(OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer() + "/api/pedidos"; // URL para obtener la lista de pedidos.
        executeRequest(url, null, "GET", onSuccess, onFailure); // Ejecuta la solicitud HTTP GET.
    }
}
