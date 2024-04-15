package es.beatkapo.app.service;

import es.beatkapo.app.response.ProductoResponse;
import es.beatkapo.app.util.NodeServer;

public class GetProductoById extends BaseService<String, ProductoResponse>{
    @Override
    protected Class<ProductoResponse> getResponseClass() {
        return ProductoResponse.class;
    }
    public void getProductoById(String idProducto, OnSuccessCallback onSuccess, OnFailureCallback onFailure){
        String url = NodeServer.getServer()+"/api/productos/"+idProducto;
        executeRequest(url, null,"GET", onSuccess, onFailure);
    }
}
