package es.beatkapo.app.util;

/**
 * Clase utilitaria para obtener la dirección del servidor Node.js.
 */
public class NodeServer {

    // Variables estáticas para definir las direcciones IP y el puerto del servidor.
    private static String PORT = "3000";
    private static String IP_LOCAL = "192.168.0.22";
    private static String IP_PUBLICA = "81.202.166.218";
    private static String IP_EMULADOR = "10.0.2.2";
    private static String IP_HOTSPOT = "192.168.229.47";

    /**
     * Obtiene la dirección del servidor.
     *
     * @return Dirección del servidor en formato de URL.
     */
    public static String getServer() {
        return "http://" + IP_PUBLICA + ":" + PORT;
    }

}
