package es.beatkapo.app.response;




public class LoginResponse extends Response{
    private String token;

    public LoginResponse(boolean error, int errorCode, String message, String token) {
        super(error, errorCode, message);
        this.token = token;
    }

    public LoginResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
