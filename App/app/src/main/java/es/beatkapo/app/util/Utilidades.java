package es.beatkapo.app.util;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import es.beatkapo.app.BaseActivity;
import es.beatkapo.app.CompleteOrder;
import es.beatkapo.app.R;
import es.beatkapo.app.model.LineaPedido;
import es.beatkapo.app.model.Pedido;
import es.beatkapo.app.model.Usuario;

public class Utilidades {
    public static final int HOME = R.id.nav_home;
    public static final int ORDERS = R.id.nav_orders;
    public static final int OPINIONS = R.id.nav_opinions;
    public static final int SETTINGS = R.id.nav_information;
    public static final int ADMIN = R.id.nav_admin;
    public static String encryptPassword(String password) {
        if(password == null) return null;
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
    public static void saveUser(Usuario user, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("app", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("user", json);
        editor.apply();
    }
    public static Usuario getUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("app", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("user", null);
        return gson.fromJson(json, Usuario.class);
    }
    public static Pedido getPedido(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("app", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("pedido", gson.toJson(new Pedido()));
        return gson.fromJson(json, Pedido.class);
    }
    public static void savePedido(Pedido pedido, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("app", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(pedido);
        editor.putString("pedido", json);
        editor.apply();
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void abrirCarrito(Context context, Pedido pedido) {
        // Abrir el PopupWindow del carrito
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.carrito, null);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        ImageButton close = popupView.findViewById(R.id.closeCartButton);
        close.setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        Button tramitar = popupView.findViewById(R.id.tramitarPedidoButton);
        tramitar.setOnClickListener(v -> {
            Intent intent = new Intent(context, CompleteOrder.class);
            context.startActivity(intent);
        });
//        popupView.setOnTouchListener((v, event) -> {
//            popupWindow.dismiss();
//            return true;
//        });
        LinearLayout carritoLayout = popupView.findViewById(R.id.cartLayout);
        if(pedido.getCantidadProductos()>0){
            carritoLayout.removeAllViews();
            for(LineaPedido linea : pedido.getLineas()){
                View lineaView = inflater.inflate(R.layout.linea_carrito, null);
                TextView nombre, cantidad, precio;
                nombre = lineaView.findViewById(R.id.nombreLineaPedido);
                cantidad = lineaView.findViewById(R.id.cantidadLineaPedido);
                precio = lineaView.findViewById(R.id.precioLineaPedido);
                nombre.setText(linea.getProducto().getNombre());
                cantidad.setText(String.valueOf("x"+linea.getCantidad()));
                //String del precio con dos decimales
                precio.setText(String.format("%.2f", linea.getTotal()) + "€");
                ImageButton delete = lineaView.findViewById(R.id.binCarrito);
                delete.setOnClickListener(v -> {
                    pedido.removeLinea(linea);
                    savePedido(pedido, context);
                    carritoLayout.removeView(lineaView);
                });
                carritoLayout.addView(lineaView);
            }
        }
        View carritoButton = ((Activity) context).findViewById(R.id.carritoButton);
        int[] location = new int[2];
        carritoButton.getLocationOnScreen(location);
        popupWindow.showAtLocation(carritoButton, Gravity.NO_GRAVITY, location[0], location[1] - popupWindow.getHeight());
    }

}

