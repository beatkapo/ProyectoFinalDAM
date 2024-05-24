package es.beatkapo.app.service;

import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.response.LoginResponse;
import es.beatkapo.app.util.NodeServer;

/**
 * Clase para manejar la solicitud de inicio de sesión en el servidor.
 */
public class LoginService extends BaseService<Usuario, LoginResponse> {

    /**
     * Realiza la solicitud de inicio de sesión al servidor.
     *
     * @param usuario   Objeto Usuario que contiene las credenciales de inicio de sesión.
     * @param onSuccess Callback llamado cuando la solicitud se completa con éxito.
     * @param onFailure Callback llamado cuando la solicitud falla.
     */
    public void login(Usuario usuario, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer() + "/api/login"; // URL para la solicitud de inicio de sesión.
        executeRequest(url, usuario, "POST", onSuccess, onFailure); // Ejecuta la solicitud HTTP POST.
    }

    /**
     * Obtiene la clase de respuesta asociada al servicio.
     *
     * @return Clase de respuesta LoginResponse.
     */
    @Override
    protected Class<LoginResponse> getResponseClass() {
        return LoginResponse.class;
    }
}
