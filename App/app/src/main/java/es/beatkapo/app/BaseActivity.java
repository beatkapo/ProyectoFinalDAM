package es.beatkapo.app;

import static es.beatkapo.app.util.Utilidades.ADMIN;
import static es.beatkapo.app.util.Utilidades.HOME;
import static es.beatkapo.app.util.Utilidades.OPINIONS;
import static es.beatkapo.app.util.Utilidades.ORDERS;
import static es.beatkapo.app.util.Utilidades.SETTINGS;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.response.UsuarioResponse;
import es.beatkapo.app.service.GetAccount;

public class BaseActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView name, email;
    private Usuario usuario;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeComponents();
        context = this;
    }
    protected  void initializeComponents() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view_home);
        drawerLayout.closeDrawer(GravityCompat.END);
        View v = navigationView.getHeaderView(0);
        name = v.findViewById(R.id.name_header);
        email = v.findViewById(R.id.email_header);
        loadUser();
        initializeMenu();
    }

    private void loadUser() {
        // Cargar el usuario en la vista
        String token = getSharedPreferences("app", MODE_PRIVATE).getString("token", "");
        if(token != null && !token.isEmpty()){
            // Obtener el usuario con el token
            GetAccount service = new GetAccount();
            service.getAccount(response -> {

                if(response == null){
                    // Mostrar mensaje de error
                    Log.e("HomeActivity", "Response is null");
                }else{
                    usuario = ((UsuarioResponse) response).getUsuario();
                    // Cargar el usuario en la vista

                    runOnUiThread(() -> {
                        name.setText(usuario.getNombre());
                        email.setText(usuario.getEmail());
                    });
                }
            }, ex -> {
                // Mostrar mensaje de error
                Log.e("HomeActivity", "Error al cargar el usuario", ex);
            });
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
                    //Intent intent = new Intent(this, OpinionsActivity.class);
                    //if(!this.getClass().equals(OpinionsActivity.class)) {
                    //    startActivity(intent);
                    //}
                } else if (id == SETTINGS){
                    //Intent intent = new Intent(this, SettingsActivity.class);
                    //if(!this.getClass().equals(SettingsActivity.class)) {
                    //    startActivity(intent);
                    //}
                } else if(id == ADMIN){
                    //Intent intent = new Intent(this, AdminActivity.class);
                    //if(!this.getClass().equals(AdminActivity.class)) {
                    //    startActivity(intent);
                    //}
                }

                return false;
            }
        });
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
}