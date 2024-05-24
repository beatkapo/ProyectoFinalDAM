package es.beatkapo.app.service;

import es.beatkapo.app.response.ImageResponse;
import es.beatkapo.app.util.NodeServer;

/**
 * Clase para obtener una imagen desde el servidor.
 */
public class GetImage extends BaseService<String, ImageResponse> {

    /**
     * Obtiene la clase de respuesta asociada al servicio.
     *
     * @return Clase de respuesta ImageResponse.
     */
    @Override
    protected Class<ImageResponse> getResponseClass() {
        return ImageResponse.class;
    }

    /**
     * Obtiene una imagen desde el servidor.
     *
     * @param urlImg     URL de la imagen.
     * @param onSuccess  Callback llamado cuando la solicitud se completa con éxito.
     * @param onFailure  Callback llamado cuando la solicitud falla.
     */
    public void getImage(String urlImg, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        String url = NodeServer.getServer() + "/api/img/" + urlImg; // URL completa de la imagen en el servidor.
        executeRequest(url, null, "GET", onSuccess, onFailure); // Ejecuta la solicitud HTTP GET.
    }
}
