package es.beatkapo.app.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import es.beatkapo.app.R;

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
            if (negativeButton != null) {
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
    public static Bitmap base64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedString);
        return BitmapFactory.decodeStream(inputStream);
    }
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    public static int getAlergenoImage(String alergeno) {
        switch (alergeno) {
            case "0":
                return R.drawable.a_gluten;
            case "1":
                return R.drawable.a_crustaceo;
            case "2":
                return R.drawable.a_huevo;
            case "3":
                return R.drawable.a_pescado;
            case "4":
                return R.drawable.a_cacahuete;
            case "5":
                return R.drawable.a_soja;
            case "6":
                return R.drawable.a_lacteos;
            case "7":
                return R.drawable.a_frutos_cascara;
            case "8":
                return R.drawable.a_apio;
            case "9":
                return R.drawable.a_mostaza;
            case "10":
                return R.drawable.a_sesamo;
            case "11":
                return R.drawable.a_moluscos;
            case "12":
                return R.drawable.a_sulfitos;
            case "13":
                return R.drawable.a_altramuces;
            default:
                return -1;
        }
    }
}

