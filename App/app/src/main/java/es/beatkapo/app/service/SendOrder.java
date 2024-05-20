package es.beatkapo.app.service;

import es.beatkapo.app.model.LineaPedido;
import es.beatkapo.app.model.Pedido;
import es.beatkapo.app.model.Producto;
import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.response.Response;
import es.beatkapo.app.util.NodeServer;

public class SendOrder extends BaseService<Pedido, Response>{
    @Override
    protected Class getResponseClass() {
        return Response.class;
    }
    public void sendOrder(Pedido pedido, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {

        for(LineaPedido l: pedido.getLineas()){
            float totalLinea = l.getCantidad() * l.getPrecio();
            pedido.setTotal(pedido.getTotal()+totalLinea);
        }
        String url = NodeServer.getServer()+"/api/saveOrder";
        executeRequest(url, pedido,"POST", onSuccess, onFailure);
    }
}
