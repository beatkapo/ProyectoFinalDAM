package es.beatkapo.app.response;

public class Response {
    private boolean error;
    private int errorCode;
    private String message;

    public Response(boolean error, int errorCode, String message) {
        this.error = error;
        this.errorCode = errorCode;
        this.message = message;
    }

    public Response() {
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
