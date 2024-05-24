package es.beatkapo.app.response;

import es.beatkapo.app.model.Usuario;

/**
 * Clase que representa la respuesta de una solicitud relacionada con un usuario.
 */
public class UsuarioResponse extends Response {
    private Usuario usuario; // Usuario asociado a la respuesta

    /**
     * Constructor de la clase UsuarioResponse.
     *
     * @param error     Indica si hubo un error en la respuesta.
     * @param errorCode Código de error, si lo hay.
     * @param message   Mensaje asociado a la respuesta.
     * @param usuario   Usuario asociado a la respuesta.
     */
    public UsuarioResponse(boolean error, int errorCode, String message, Usuario usuario) {
        super(error, errorCode, message);
        this.usuario = usuario;
    }

    /**
     * Constructor vacío para inicializar un objeto UsuarioResponse sin parámetros.
     */
    public UsuarioResponse() {
    }

    /**
     * Obtiene el usuario asociado a la respuesta.
     *
     * @return Usuario asociado a la respuesta.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario asociado a la respuesta.
     *
     * @param usuario Usuario asociado a la respuesta.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
