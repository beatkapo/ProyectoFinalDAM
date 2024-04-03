package es.beatkapo.app.service;

import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.response.LoginResponse;
import es.beatkapo.app.util.NodeServer;

public class LoginService extends BaseService<Usuario,LoginResponse> {
    public void login(Usuario usuario, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer()+"/api/login";
        executeRequest(url, usuario, onSuccess, onFailure);
    }

    @Override
    protected Class<LoginResponse> getResponseClass() {
        return LoginResponse.class;
    }
}