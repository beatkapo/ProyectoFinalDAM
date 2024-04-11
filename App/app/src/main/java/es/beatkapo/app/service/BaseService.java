package es.beatkapo.app.service;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;

import es.beatkapo.app.response.LoginResponse;
import es.beatkapo.app.util.ServiceUtils;

public abstract class BaseService<T, R> {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    protected void executeRequest(String url, T requestObject, String method, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        Gson gson = new Gson();

        CompletableFuture.supplyAsync(() -> {

                    try {
                        String json = null;
                        if (requestObject != null) {
                            json = gson.toJson(requestObject);
                        }
                        String response = ServiceUtils.getResponse(url, json, method);
                        return gson.fromJson(response, getResponseClass());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, executorService)
                .thenAccept(response -> {
                    // Llama al callback onSuccess cuando la operación se complete con éxito
                    handler.post(() -> {
                        onSuccess.onSuccess(response);

                    });
                })
                .exceptionally(ex -> {
                    // Llama al callback onFailure cuando ocurre una excepción
                    handler.post(() -> onFailure.onFailure(ex));
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
