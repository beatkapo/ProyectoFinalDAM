package es.beatkapo.app.service;

import es.beatkapo.app.response.CategoriasResponse;
import es.beatkapo.app.util.NodeServer;

/**
 * Clase para obtener la lista de categorías desde el servidor.
 */
public class GetCategoriasService extends BaseService<Void, CategoriasResponse> {

    /**
     * Obtiene la clase de respuesta asociada al servicio.
     *
     * @return Clase de respuesta CategoriasResponse.
     */
    @Override
    protected Class<CategoriasResponse> getResponseClass() {
        return CategoriasResponse.class;
    }

    /**
     * Obtiene la lista de categorías desde el servidor.
     *
     * @param onSuccess Callback llamado cuando la solicitud se completa con éxito.
     * @param onFailure Callback llamado cuando la solicitud falla.
     */
    public void getCategorias(OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer() + "/api/categorias"; // URL del servidor para obtener la lista de categorías.
        executeRequest(url, null, "GET", onSuccess, onFailure); // Ejecuta la solicitud HTTP GET.
    }
}
