package es.beatkapo.app.response;

public class ImageResponse extends Response{
    private String image;

    public ImageResponse(boolean error,int errorCode, String message, String image) {
        super(error, errorCode, message);
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
