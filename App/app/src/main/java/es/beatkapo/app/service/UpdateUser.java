package es.beatkapo.app.service;

import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.response.Response;
import es.beatkapo.app.util.NodeServer;

/**
 * Clase para actualizar la información del usuario en el servidor.
 */
public class UpdateUser extends BaseService<Usuario, Response> {

    /**
     * Realiza una solicitud para cambiar la contraseña del usuario en el servidor.
     *
     * @param usuario    Objeto Usuario que contiene la información del usuario y la nueva contraseña.
     * @param onSuccess  Callback llamado cuando la solicitud se completa con éxito.
     * @param onFailure  Callback llamado cuando la solicitud falla.
     */
    public void changePassword(Usuario usuario, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer() + "/api/usuario"; // URL para la solicitud de cambio de contraseña.
        executeRequest(url, usuario, "PATCH", onSuccess, onFailure); // Ejecuta la solicitud HTTP PATCH.
    }

    /**
     * Realiza una solicitud para actualizar la información del usuario en el servidor.
     *
     * @param usuario    Objeto Usuario que contiene la información actualizada del usuario.
     * @param onSuccess  Callback llamado cuando la solicitud se completa con éxito.
     * @param onFailure  Callback llamado cuando la solicitud falla.
     */
    public void updateUser(Usuario usuario, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer() + "/api/usuario"; // URL para la solicitud de actualización de usuario.
        executeRequest(url, usuario, "PUT", onSuccess, onFailure); // Ejecuta la solicitud HTTP PUT.
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
