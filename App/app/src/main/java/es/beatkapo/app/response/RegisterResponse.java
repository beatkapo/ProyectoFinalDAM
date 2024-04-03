package es.beatkapo.app.response;

public class RegisterResponse extends Response{
    private String id;

    public RegisterResponse(boolean error, int errorCode, String message, String id) {
        super(error, errorCode, message);
        this.id = id;
    }

    public RegisterResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
