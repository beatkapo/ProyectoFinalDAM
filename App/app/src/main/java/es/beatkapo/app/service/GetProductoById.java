package es.beatkapo.app.service;

import es.beatkapo.app.response.ProductoResponse;
import es.beatkapo.app.util.NodeServer;

/**
 * Clase para obtener un producto por su identificador desde el servidor.
 */
public class GetProductoById extends BaseService<String, ProductoResponse> {

    /**
     * Obtiene la clase de respuesta asociada al servicio.
     *
     * @return Clase de respuesta ProductoResponse.
     */
    @Override
    protected Class<ProductoResponse> getResponseClass() {
        return ProductoResponse.class;
    }

    /**
     * Obtiene un producto por su identificador desde el servidor.
     *
     * @param idProducto Identificador del producto.
     * @param onSuccess  Callback llamado cuando la solicitud se completa con éxito.
     * @param onFailure  Callback llamado cuando la solicitud falla.
     */
    public void getProductoById(String idProducto, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer() + "/api/productos/" + idProducto; // URL para obtener el producto por su identificador.
        executeRequest(url, null, "GET", onSuccess, onFailure); // Ejecuta la solicitud HTTP GET.
    }
}
