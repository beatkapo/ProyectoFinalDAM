package es.beatkapo.app.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringJoiner;
import java.util.zip.GZIPInputStream;

/**
 * Clase de utilidad para realizar solicitudes HTTP y obtener respuestas del servidor.
 */
public class ServiceUtils {
    private static String token = null;

    /**
     * Establece el token de autorización para las solicitudes HTTP.
     * @param token Token de autorización.
     */
    public static void setToken(String token) {
        ServiceUtils.token = token;
    }

    /**
     * Elimina el token de autorización actualmente configurado.
     */
    public static void removeToken() {
        ServiceUtils.token = null;
    }

    /**
     * Obtiene el conjunto de caracteres de la codificación del contenido HTTP.
     * @param contentType Tipo de contenido HTTP.
     * @return Conjunto de caracteres de la codificación del contenido.
     */
    public static String getCharset(String contentType) {
        for (String param : contentType.replace(" ", "").split(";")) {
            if (param.startsWith("charset=")) {
                return param.split("=", 2)[1];
            }
        }
        return null;
    }

    /**
     * Realiza una solicitud HTTP y devuelve la respuesta del servidor.
     * @param url URL a la que se realizará la solicitud.
     * @param data Datos que se enviarán en la solicitud (puede ser nulo).
     * @param method Método HTTP para la solicitud (GET, POST, etc.).
     * @return Respuesta del servidor como una cadena de texto.
     */
    public static String getResponse(String url, String data, String method) {
        BufferedReader bufInput = null;
        StringJoiner result = new StringJoiner("\n");
        try {
            URL urlConn = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlConn.openConnection();
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(method);

            // Configuración de encabezados de solicitud
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
            conn.setRequestProperty("Accept-Language", "es-ES,es;q=0.8");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36");

            // Si hay un token de autorización, lo agrega a la solicitud
            if (token != null) {
                conn.setRequestProperty("Authorization", "Bearer " + token);
            }

            // Si hay datos que enviar, configura la solicitud como de salida y envía los datos
            if (data != null) {
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Content-Length", Integer.toString(data.getBytes().length));
                conn.setDoOutput(true);
                // Envía la solicitud
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.write(data.getBytes());
                wr.flush();
                wr.close();
            }

            // Obtiene el conjunto de caracteres de la codificación del contenido HTTP
            String charset = getCharset(conn.getHeaderField("Content-Type"));

            // Procesa la respuesta del servidor
            if (charset != null) {
                InputStream input = conn.getInputStream();
                if ("gzip".equals(conn.getContentEncoding())) {
                    input = new GZIPInputStream(input);
                } else
                    bufInput = new BufferedReader(
                            new InputStreamReader(input));

                // Lee la respuesta línea por línea y la agrega al resultado
                String line;
                while ((line = bufInput.readLine()) != null) {
                    result.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufInput != null) {
                try {
                    bufInput.close();
                } catch (IOException e) {
                }
            }
        }

        // Devuelve la respuesta del servidor como una cadena de texto
        return result.toString();
    }
}
