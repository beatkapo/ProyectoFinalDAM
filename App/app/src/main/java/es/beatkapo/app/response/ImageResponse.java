package es.beatkapo.app.response;

/**
 * Clase que representa la respuesta de una solicitud de imagen.
 */
public class ImageResponse extends Response {
    private String image; // La URL de la imagen recibida en la respuesta

    /**
     * Constructor de la clase ImageResponse.
     *
     * @param error     Indica si hubo un error en la respuesta.
     * @param errorCode Código de error, si lo hay.
     * @param message   Mensaje asociado a la respuesta.
     * @param image     URL de la imagen recibida en la respuesta.
     */
    public ImageResponse(boolean error, int errorCode, String message, String image) {
        super(error, errorCode, message);
        this.image = image;
    }

    /**
     * Obtiene la URL de la imagen recibida en la respuesta.
     *
     * @return URL de la imagen.
     */
    public String getImage() {
        return image;
    }

    /**
     * Establece la URL de la imagen recibida en la respuesta.
     *
     * @param image URL de la imagen.
     */
    public void setImage(String image) {
        this.image = image;
    }
}
