package es.beatkapo.app.service;

import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.response.UsuarioResponse;
import es.beatkapo.app.util.NodeServer;

public class GetAccount extends BaseService<Void, UsuarioResponse>{

    @Override
    protected Class<UsuarioResponse> getResponseClass() {
        return UsuarioResponse.class;
    }

    public void getAccount(OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer()+"/api/usuario";
        executeRequest(url, null,"GET", onSuccess, onFailure);
    }
}
