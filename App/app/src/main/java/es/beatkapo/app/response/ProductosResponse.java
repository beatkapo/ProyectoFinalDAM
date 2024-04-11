package es.beatkapo.app.response;

import java.util.List;

import es.beatkapo.app.dto.ProductoDTO;
import es.beatkapo.app.model.Producto;

public class ProductosResponse extends Response{
    private List<ProductoDTO> productos;

    public ProductosResponse(boolean error, int errorCode, String message, List<ProductoDTO> productos) {
        super(error, errorCode, message);
        this.productos = productos;
    }

    public ProductosResponse() {
    }

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }
}
