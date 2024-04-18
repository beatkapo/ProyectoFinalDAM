package es.beatkapo.app.response;

import es.beatkapo.app.model.Usuario;

public class UsuarioResponse extends Response{
    private Usuario usuario;

    public UsuarioResponse(boolean error, int errorCode, String message, Usuario usuario) {
        super(error, errorCode, message);
        this.usuario = usuario;
    }

    public UsuarioResponse() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
