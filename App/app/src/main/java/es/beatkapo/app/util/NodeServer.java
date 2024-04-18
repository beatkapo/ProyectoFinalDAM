package es.beatkapo.app.util;

public class NodeServer {

    private static String PORT = "3000";
    private static String IP_LOCAL = "192.168.0.22";
    private static String IP_PUBLICA = "81.202.166.218";
    private static String IP_EMULADOR = "10.0.2.2";
    public static String getServer()
    {
        return "http://"+ IP_LOCAL +":"+PORT;//Si la ip canvia (no es ninguna de les variables), revisar network-security-config.xml
    }

}