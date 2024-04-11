package es.beatkapo.app.util;

public class NodeServer {

    private static String PORT = "3000";
    private static String IP= "192.168.0.22";
    public static String getServer()
    {
        return "http://"+IP+":"+PORT;//Si la ip canvia (no es ninguna de les variables), revisar network-security-config.xml
    }

}