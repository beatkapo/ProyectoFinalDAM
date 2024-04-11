package es.beatkapo.app.service;

import es.beatkapo.app.response.CategoriasResponse;
import es.beatkapo.app.util.NodeServer;

public class GetCategoriasService extends BaseService<Void, CategoriasResponse>{
    @Override
    protected Class<CategoriasResponse> getResponseClass() {
        return CategoriasResponse.class;
    }
    public void getCategorias(OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer()+"/api/categorias";
        executeRequest(url, null,"GET", onSuccess, onFailure);
    }
}
