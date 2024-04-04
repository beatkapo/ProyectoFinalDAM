package es.beatkapo.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeComponents();
    }

    private void initializeComponents() {
        drawerLayout = findViewById(R.id.drawerLayout_home);
        navigationView = findViewById(R.id.nav_view_home);
        btnMenu = findViewById(R.id.menuButton_home);
        drawerLayout.closeDrawer(GravityCompat.END);
    }
    public void showMenu(View v){
        drawerLayout.openDrawer(GravityCompat.END);
    }

}