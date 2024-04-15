package es.beatkapo.app.response;

import java.util.List;

import es.beatkapo.app.model.Producto;

public class ProductosResponse extends Response{
    private List<Producto> productos;

    public ProductosResponse(boolean error, int errorCode, String message, List<Producto> productos) {
        super(error, errorCode, message);
        this.productos = productos;
    }

    public ProductosResponse() {
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
