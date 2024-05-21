package es.beatkapo.app.service;

import java.util.List;

import es.beatkapo.app.model.Pedido;
import es.beatkapo.app.response.PedidosResponse;
import es.beatkapo.app.util.NodeServer;

public class GetPedidos extends BaseService<Void, PedidosResponse>{
    @Override
    protected Class getResponseClass() {
        return PedidosResponse.class;
    }
    public void getProductos(OnSuccessCallback onSuccess, OnFailureCallback onFailure){
        String url = NodeServer.getServer()+"/api/pedidos";
        executeRequest(url, null,"GET", onSuccess, onFailure);
    }
}
