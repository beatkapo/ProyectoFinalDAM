package es.beatkapo.app.util;

public class NodeServer {

    private static String PORT = "3000";
    private static String IP_CASA = "192.168.0.22";
    private static String IP_BROMERA = "192.168.100.34";
    private static String IP_EMULADOR = "10.0.2.2";
    public static String getServer()
    {
        return "http://"+IP_EMULADOR+":"+PORT;//Si la ip canvia (no es ninguna de les variables), revisar network-security-config.xml
    }

}