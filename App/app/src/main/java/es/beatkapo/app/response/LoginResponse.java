package es.beatkapo.app.response;

/**
 * Clase que representa la respuesta de un intento de inicio de sesión.
 */
public class LoginResponse extends Response {
    private String token; // El token de acceso recibido en la respuesta

    /**
     * Constructor de la clase LoginResponse.
     *
     * @param error     Indica si hubo un error en la respuesta.
     * @param errorCode Código de error, si lo hay.
     * @param message   Mensaje asociado a la respuesta.
     * @param token     Token de acceso recibido en la respuesta.
     */
    public LoginResponse(boolean error, int errorCode, String message, String token) {
        super(error, errorCode, message);
        this.token = token;
    }

    /**
     * Constructor vacío para inicializar un objeto LoginResponse sin parámetros.
     */
    public LoginResponse() {
    }

    /**
     * Obtiene el token de acceso recibido en la respuesta.
     *
     * @return Token de acceso.
     */
    public String getToken() {
        return token;
    }

    /**
     * Establece el token de acceso recibido en la respuesta.
     *
     * @param token Token de acceso.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
