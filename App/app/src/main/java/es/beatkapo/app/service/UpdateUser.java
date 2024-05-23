package es.beatkapo.app.service;

import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.response.Response;
import es.beatkapo.app.util.NodeServer;

public class UpdateUser extends BaseService<Usuario, Response> {
    @Override
    protected Class<Response> getResponseClass() {
        return Response.class;
    }
    public void changePassword(Usuario usuario, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer()+"/api/usuario";
        executeRequest(url, usuario,"PATCH", onSuccess, onFailure);
    }
    public void updateUser(Usuario usuario, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer()+"/api/usuario";
        executeRequest(url, usuario,"PUT", onSuccess, onFailure);
    }
}
