package es.beatkapo.app.response;

import java.util.List;

import es.beatkapo.app.model.Pedido;
import es.beatkapo.app.model.Producto;

public class PedidosResponse extends Response{
    private List<Pedido> pedidos;
    public PedidosResponse(boolean error, int errorCode, String message, List<Pedido> pedidos) {
        super(error, errorCode, message);
        this.pedidos = pedidos;
    }
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
