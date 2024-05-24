package es.beatkapo.app.service;

import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.response.RegisterResponse;
import es.beatkapo.app.util.NodeServer;

/**
 * Clase para manejar la solicitud de registro de usuarios en el servidor.
 */
public class RegisterService extends BaseService<Usuario, RegisterResponse> {

    /**
     * Realiza la solicitud de registro de usuario al servidor.
     *
     * @param usuario   Objeto Usuario que contiene la información del usuario a registrar.
     * @param onSuccess Callback llamado cuando la solicitud se completa con éxito.
     * @param onFailure Callback llamado cuando la solicitud falla.
     */
    public void register(Usuario usuario, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer() + "/api/register"; // URL para la solicitud de registro.
        executeRequest(url, usuario, "POST", onSuccess, onFailure); // Ejecuta la solicitud HTTP POST.
    }

    /**
     * Obtiene la clase de respuesta asociada al servicio.
     *
     * @return Clase de respuesta RegisterResponse.
     */
    @Override
    protected Class<RegisterResponse> getResponseClass() {
        return RegisterResponse.class;
    }
}
