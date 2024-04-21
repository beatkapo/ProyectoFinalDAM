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
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.util.Utilidades;

public class BaseActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView name, email;
    protected Usuario user;
    private Context context;
    private ImageButton btnMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeComponents();
        context = this;
    }
    protected void initializeComponents() {
        btnMenu = findViewById(R.id.menuButton);

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
        switch (user.getTipoUsuario()){
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
}