package es.beatkapo.app.response;

/**
 * Clase que representa la respuesta de un intento de registro de usuario.
 */
public class RegisterResponse extends Response {
    private String id; // Identificador único del usuario registrado

    /**
     * Constructor de la clase RegisterResponse.
     *
     * @param error     Indica si hubo un error en la respuesta.
     * @param errorCode Código de error, si lo hay.
     * @param message   Mensaje asociado a la respuesta.
     * @param id        Identificador único del usuario registrado.
     */
    public RegisterResponse(boolean error, int errorCode, String message, String id) {
        super(error, errorCode, message);
        this.id = id;
    }

    /**
     * Constructor vacío para inicializar un objeto RegisterResponse sin parámetros.
     */
    public RegisterResponse() {
    }

    /**
     * Obtiene el identificador único del usuario registrado.
     *
     * @return Identificador único del usuario.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador único del usuario registrado.
     *
     * @param id Identificador único del usuario.
     */
    public void setId(String id) {
        this.id = id;
    }
}
