package es.beatkapo.app.response;

import es.beatkapo.app.model.Producto;

public class ProductoResponse extends Response{
    private Producto producto;

    public ProductoResponse(boolean error, int errorCode, String message, Producto producto) {
        super(error, errorCode, message);
        this.producto = producto;
    }

    public ProductoResponse() {
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
