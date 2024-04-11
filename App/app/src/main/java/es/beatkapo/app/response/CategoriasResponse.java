package es.beatkapo.app.response;

import java.util.List;

import es.beatkapo.app.model.Categoria;

public class CategoriasResponse extends Response{
    private List<Categoria> categorias;

    public CategoriasResponse(boolean error, int errorCode, String message, List<Categoria> categorias) {
        super(error, errorCode, message);
        this.categorias = categorias;
    }

    public CategoriasResponse() {
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
