package es.beatkapo.app.service;

import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.response.LoginResponse;
import es.beatkapo.app.response.RegisterResponse;
import es.beatkapo.app.util.NodeServer;

public class RegisterService extends BaseService<Usuario, RegisterResponse> {
    public void register(Usuario usuario, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer()+"/api/register";
        executeRequest(url, usuario, onSuccess, onFailure);
    }

    @Override
    protected Class<RegisterResponse> getResponseClass() {
        return RegisterResponse.class;
    }
}
