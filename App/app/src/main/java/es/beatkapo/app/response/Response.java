package es.beatkapo.app.response;

/**
 * Clase base que representa la respuesta de una solicitud.
 */
public class Response {
    private boolean error; // Indica si hubo un error en la respuesta
    private int errorCode; // Código de error, si lo hay
    private String message; // Mensaje asociado a la respuesta

    /**
     * Constructor de la clase Response.
     *
     * @param error     Indica si hubo un error en la respuesta.
     * @param errorCode Código de error, si lo hay.
     * @param message   Mensaje asociado a la respuesta.
     */
    public Response(boolean error, int errorCode, String message) {
        this.error = error;
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * Constructor vacío para inicializar un objeto Response sin parámetros.
     */
    public Response() {
    }

    /**
     * Obtiene el código de error de la respuesta.
     *
     * @return Código de error.
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Establece el código de error de la respuesta.
     *
     * @param errorCode Código de error.
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Verifica si la respuesta contiene un error.
     *
     * @return true si hay un error, false si no.
     */
    public boolean isError() {
        return error;
    }

    /**
     * Establece si hay un error en la respuesta.
     *
     * @param error true si hay un error, false si no.
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * Obtiene el mensaje asociado a la respuesta.
     *
     * @return Mensaje de la respuesta.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Establece el mensaje asociado a la respuesta.
     *
     * @param message Mensaje de la respuesta.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
