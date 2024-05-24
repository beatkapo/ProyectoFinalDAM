package es.beatkapo.app.service;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.beatkapo.app.response.LoginResponse;
import es.beatkapo.app.util.ServiceUtils;

/**
 * Clase base para servicios de solicitud HTTP genéricos.
 *
 * @param <T> Tipo de objeto de solicitud.
 * @param <R> Tipo de objeto de respuesta.
 */
public abstract class BaseService<T, R> {
    private ExecutorService executorService = Executors.newSingleThreadExecutor(); // ExecutorService para ejecutar solicitudes en un hilo separado.
    private Handler handler = new Handler(Looper.getMainLooper()); // Handler para manejar callbacks en el hilo principal de la IU.

    /**
     * Ejecuta una solicitud HTTP asincrónica.
     *
     * @param url          URL a la que se enviará la solicitud.
     * @param requestObject Objeto de solicitud.
     * @param method       Método de la solicitud (GET, POST, PUT, etc.).
     * @param onSuccess    Callback llamado cuando la solicitud se completa con éxito.
     * @param onFailure    Callback llamado cuando la solicitud falla.
     */
    protected void executeRequest(String url, T requestObject, String method, OnSuccessCallback onSuccess, OnFailureCallback onFailure) {
        Gson gson = new Gson(); // Instancia de Gson para serializar/deserializar objetos.

        CompletableFuture.supplyAsync(() -> {
                    try {
                        String json = null;
                        if (requestObject != null) {
                            json = gson.toJson(requestObject); // Convierte el objeto de solicitud a formato JSON.
                        }
                        String response = ServiceUtils.getResponse(url, json, method); // Realiza la solicitud HTTP y obtiene la respuesta como una cadena JSON.
                        return gson.fromJson(response, getResponseClass()); // Deserializa la respuesta JSON en un objeto de la clase de respuesta.
                    } catch (Exception e) {
                        throw new RuntimeException(e); // Lanza una excepción si ocurre un error durante la solicitud.
                    }
                }, executorService)
                .thenAccept(response -> {
                    // Llama al callback onSuccess cuando la operación se complete con éxito
                    handler.post(() -> onSuccess.onSuccess(response));
                })
                .exceptionally(ex -> {
                    // Llama al callback onFailure cuando ocurre una excepción
                    handler.post(() -> onFailure.onFailure(ex));
                    return null;
                });
    }

    /**
     * Obtiene la clase de respuesta asociada al servicio.
     *
     * @return Clase de respuesta.
     */
    protected abstract Class<R> getResponseClass();

    /**
     * Interfaz para el callback llamado cuando la solicitud se completa con éxito.
     */
    public interface OnSuccessCallback {
        void onSuccess(Object response);
    }

    /**
     * Interfaz para el callback llamado cuando la solicitud falla.
     */
    public interface OnFailureCallback {
        void onFailure(Throwable ex);
    }

}
