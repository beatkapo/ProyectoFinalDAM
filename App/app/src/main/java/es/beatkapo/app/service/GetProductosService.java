package es.beatkapo.app.service;

import es.beatkapo.app.response.ProductosResponse;
import es.beatkapo.app.util.NodeServer;

/**
 * Clase para obtener la lista de productos desde el servidor.
 */
public class GetProductosService extends BaseService<Void, ProductosResponse> {

    /**
     * Obtiene la clase de respuesta asociada al servicio.
     *
     * @return Clase de respuesta ProductosResponse.
     */
    @Override
    protected Class<ProductosResponse> getResponseClass() {
        return ProductosResponse.class;
    }

    /**
     * Obtiene la lista de productos desde el servidor.
     *
     * @param onSuccess Callback llamado cuando la solicitud se completa con éxito.
     * @param onFailure Callback llamado cuando la solicitud falla.
     */
    public void getProductos(OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer() + "/api/productos"; // URL para obtener la lista de productos.
        executeRequest(url, null, "GET", onSuccess, onFailure); // Ejecuta la solicitud HTTP GET.
    }
}
