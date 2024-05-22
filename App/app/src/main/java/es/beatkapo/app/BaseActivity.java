package es.beatkapo.app;

import static es.beatkapo.app.util.Utilidades.ADMIN;
import static es.beatkapo.app.util.Utilidades.HOME;
import static es.beatkapo.app.util.Utilidades.OPINIONS;
import static es.beatkapo.app.util.Utilidades.ORDERS;
import static es.beatkapo.app.util.Utilidades.SETTINGS;
import static es.beatkapo.app.util.Utilidades.savePedido;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import es.beatkapo.app.model.LineaPedido;
import es.beatkapo.app.model.Pedido;
import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.util.Utilidades;

public class BaseActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView name, email, cantidadCarritoText;
    protected Usuario user;
    protected Context context;
    private ImageButton btnMenu;
    private FloatingActionButton carritoButton;

    protected Pedido pedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeComponents();
        context = this;
    }
    protected void initializeComponents() {

        pedido = Utilidades.getPedido(this);
        btnMenu = findViewById(R.id.menuButton);
        carritoButton = findViewById(R.id.carritoButton);

        if (carritoButton != null) {
            carritoButton.setOnClickListener(v -> {
                abrirCarrito(pedido);
            });
            cantidadCarritoText = findViewById(R.id.cantidad_carrito_textView);
            actualizarCantidadCarrito();
        }

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        if(drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
        View v = navigationView.getHeaderView(0);
        name = v.findViewById(R.id.name_header);
        email = v.findViewById(R.id.email_header);
        loadUser();

    }

    protected void actualizarCantidadCarrito() {
        pedido = Utilidades.getPedido(this);
        if(pedido == null){
            cantidadCarritoText.setText("0");
        }else{
            if(cantidadCarritoText != null){
                cantidadCarritoText.setText(String.valueOf(pedido.getCantidadProductos()));
            }

        }
    }

    private void loadUser() {
        // Cargar el usuario en la vista

        user = Utilidades.getUser(this);
        if(user != null){
            name.setText(user.getNombre());
            email.setText(user.getEmail());
            initializeMenu();
        }
    }
    private void initializeMenu() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Log.d("NavigationView", "Item selected: " + item.getItemId());
                if(id == HOME){
                    Intent intent = new Intent(context, HomeActivity.class);
                    if(!context.getClass().equals(HomeActivity.class)) {
                        startActivity(intent);
                    }
                }else if(id == ORDERS){
                    Intent intent = new Intent(context, OrdersActivity.class);
                    if(!context.getClass().equals(OrdersActivity.class)) {
                        startActivity(intent);
                    }
                }else if(id == OPINIONS){
                    //Intent intent = new Intent(context, OpinionsActivity.class);
                    //if(!context.getClass().equals(OpinionsActivity.class)) {
                    //    startActivity(intent);
                    //}
                } else if (id == SETTINGS){
                    Intent intent = new Intent(context, InformationActivity.class);
                    if(!context.getClass().equals(InformationActivity.class)) {
                        startActivity(intent);
                    }
                } else if(id == ADMIN){
                    Intent intent = new Intent(context, AdminHomeActivity.class);
                    if(!context.getClass().equals(AdminHomeActivity.class)) {
                        startActivity(intent);
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.END);
                return true;
            }
        });
        switch (user.getUserType()){
            case 2:
                navigationView.getMenu().findItem(ADMIN).setVisible(true);
                break;
            case 1:
                navigationView.getMenu().findItem(ADMIN).setVisible(true);
                break;
            default:
                navigationView.getMenu().findItem(ADMIN).setVisible(false);
                break;

        }
    }
    public void showMenu(View v){
        drawerLayout.openDrawer(GravityCompat.END);
    }

    public void closeMenu(View v){
        drawerLayout.closeDrawer(GravityCompat.END);
    }
    protected void setContext(Context context){
        this.context = context;
    }
    @SuppressLint("MissingInflatedId")
    protected void abrirCarrito(Pedido pedido) {
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
            if(pedido.getCantidadProductos() == 0){
                Toast.makeText(context, R.string.carritoVacio, Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(context, CompleteOrder.class);
            context.startActivity(intent);
            popupWindow.dismiss();
        });
        popupWindow.setOnDismissListener(() -> {
            savePedido(pedido, context);
            actualizarCantidadCarrito();
        });
//        popupView.setOnTouchListener((v, event) -> {
//            popupWindow.dismiss();
//            return true;
//        });
        LinearLayout carritoLayout = popupView.findViewById(R.id.cartLayout);
        if(pedido.getCantidadProductos()>0){
            actualizarTotalCarrito(pedido, popupView);
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
                    if(pedido.getCantidadProductos() == 0){
                        popupWindow.dismiss();
                        abrirCarrito(pedido);
                    }
                    actualizarTotalCarrito(pedido, popupView);
                });
                carritoLayout.addView(lineaView);

            }
        }
        View carritoButton = ((Activity) context).findViewById(R.id.carritoButton);
        int[] location = new int[2];
        carritoButton.getLocationOnScreen(location);
        popupWindow.showAtLocation(carritoButton, Gravity.NO_GRAVITY, location[0], location[1] - popupWindow.getHeight());
    }
    public void actualizarTotalCarrito(Pedido pedido, View popupView) {
        TextView total = popupView.findViewById(R.id.totalCart);
        total.setText(String.format("%.2f", pedido.getTotal()) + "€");
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarCantidadCarrito();
    }
}