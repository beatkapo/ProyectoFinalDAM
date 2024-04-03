package es.beatkapo.app.service;

import com.google.gson.Gson;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.beatkapo.app.response.LoginResponse;
import es.beatkapo.app.util.ServiceUtils;

public abstract class BaseService<T, R> {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    protected void executeRequest(String url, T requestObject, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        Gson gson = new Gson();

            CompletableFuture.supplyAsync(() -> {

                        try{
                            String json = gson.toJson(requestObject);
                            String response = ServiceUtils.getResponse(url, json, "POST");
                            return gson.fromJson(response, getResponseClass());
                        }catch (Exception e){
                            throw new RuntimeException(e);
                        }
                    }, executorService)
                    .thenAccept(response -> {
                        // Llama al callback onSuccess cuando la operación se complete con éxito
                        onSuccess.onSuccess(response);
                    })
                    .exceptionally(ex -> {
                        // Llama al callback onFailure cuando ocurre una excepción
                        onFailure.onFailure(ex);
                        return null;
                    });

    }

    protected abstract Class<R> getResponseClass();

    public interface OnSuccessCallback {
        void onSuccess(Object response);
    }

    public interface OnFailureCallback {
        void onFailure(Throwable ex);
    }
}
