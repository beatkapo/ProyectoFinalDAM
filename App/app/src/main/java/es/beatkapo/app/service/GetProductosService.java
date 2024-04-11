package es.beatkapo.app.service;

import es.beatkapo.app.model.Categoria;
import es.beatkapo.app.response.ProductosResponse;
import es.beatkapo.app.util.NodeServer;

public class GetProductosService extends BaseService<Void, ProductosResponse>{
    @Override
    protected Class<ProductosResponse> getResponseClass() {
        return ProductosResponse.class;
    }
    public void getProductos(OnSuccessCallback onSuccess, OnFailureCallback onFailure){
        String url = NodeServer.getServer()+"/api/productos";
        executeRequest(url, null,"GET", onSuccess, onFailure);
    }
}
