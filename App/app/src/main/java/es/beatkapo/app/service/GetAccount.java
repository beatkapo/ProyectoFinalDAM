package es.beatkapo.app.service;

import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.response.UsuarioResponse;
import es.beatkapo.app.util.NodeServer;

/**
 * Clase para obtener la información de la cuenta del usuario desde el servidor.
 */
public class GetAccount extends BaseService<Void, UsuarioResponse> {

    /**
     * Obtiene la clase de respuesta asociada al servicio.
     *
     * @return Clase de respuesta UsuarioResponse.
     */
    @Override
    protected Class<UsuarioResponse> getResponseClass() {
        return UsuarioResponse.class;
    }

    /**
     * Obtiene la información de la cuenta del usuario desde el servidor.
     *
     * @param onSuccess Callback llamado cuando la solicitud se completa con éxito.
     * @param onFailure Callback llamado cuando la solicitud falla.
     */
    public void getAccount(OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer() + "/api/usuario"; // URL del servidor para obtener la información de la cuenta del usuario.
        executeRequest(url, null, "GET", onSuccess, onFailure); // Ejecuta la solicitud HTTP GET.
    }
}
