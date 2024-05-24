package es.beatkapo.app.response;

import java.util.List;

import es.beatkapo.app.model.Categoria;

/**
 * Clase que representa la respuesta de una solicitud de categorías.
 */
public class CategoriasResponse extends Response {
    private List<Categoria> categorias; // Lista de categorías recibidas en la respuesta

    /**
     * Constructor de la clase CategoriasResponse.
     *
     * @param error     Indica si hubo un error en la respuesta.
     * @param errorCode Código de error, si lo hay.
     * @param message   Mensaje asociado a la respuesta.
     * @param categorias Lista de categorías recibidas en la respuesta.
     */
    public CategoriasResponse(boolean error, int errorCode, String message, List<Categoria> categorias) {
        super(error, errorCode, message);
        this.categorias = categorias;
    }

    /**
     * Constructor vacío para inicializar un objeto CategoriasResponse sin parámetros.
     */
    public CategoriasResponse() {
    }

    /**
     * Obtiene la lista de categorías recibidas en la respuesta.
     *
     * @return Lista de categorías.
     */
    public List<Categoria> getCategorias() {
        return categorias;
    }

    /**
     * Establece la lista de categorías recibidas en la respuesta.
     *
     * @param categorias Lista de categorías.
     */
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
