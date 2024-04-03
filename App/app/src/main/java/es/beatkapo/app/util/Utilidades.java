package es.beatkapo.app.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilidades {
    public static String encryptPassword(String password) {
        try {
            // Crear una instancia de MessageDigest con el algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Obtener el arreglo de bytes de la contraseña
            byte[] hash = digest.digest(password.getBytes());

            // Convertir el arreglo de bytes a una cadena hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void showAlert(Activity activity, String title, String message, String positiveButton, DialogInterface.OnClickListener positiveListener, String negativeButton, DialogInterface.OnClickListener negativeListener){
        if(activity == null) {
            Log.e("showAlert", "La actividad es null");
            return;
        }
        activity.runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton(positiveButton, positiveListener);
            if (negativeButton != null && negativeListener != null) {
                builder.setNegativeButton(negativeButton, negativeListener);
            }
            AlertDialog dialog = builder.create();
            System.out.println("El diálogo se ha intentado crear");
            if(dialog != null) {
                System.out.println("El diálogo se ha creado correctamente");
                dialog.show();
            } else {
                System.out.println("El diálogo no se ha creado correctamente");
            }
        });
    }
}

