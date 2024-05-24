package es.beatkapo.app.service;

import es.beatkapo.app.response.ImageResponse;
import es.beatkapo.app.util.NodeServer;

public class GetImage extends BaseService<String, ImageResponse>{
    @Override
    protected Class getResponseClass() {
        return ImageResponse.class;
    }
    public void getImage(String urlImg, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {

        String url = NodeServer.getServer()+"/api/img/"+urlImg;
        executeRequest(url, null,"GET", onSuccess, onFailure);
    }
}
