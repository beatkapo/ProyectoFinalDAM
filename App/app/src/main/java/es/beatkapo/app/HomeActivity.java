package es.beatkapo.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.stream.Collectors;

import es.beatkapo.app.adapter.HomeListAdapter;
import es.beatkapo.app.model.Producto;
import es.beatkapo.app.model.Categoria;
import es.beatkapo.app.response.CategoriasResponse;
import es.beatkapo.app.response.ProductosResponse;
import es.beatkapo.app.service.GetCategoriasService;
import es.beatkapo.app.service.GetProductosService;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMenu;
    private LinearLayout homeLayout;
    private List<Categoria> categorias;
    private List<Producto> productos;

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
        homeLayout = findViewById(R.id.homeLayout);

        loadData();
        Log.e("HomeActivity", "Data loaded");
    }
    public void showMenu(View v){
        drawerLayout.openDrawer(GravityCompat.END);
    }

    public void closeMenu(View v){
        drawerLayout.closeDrawer(GravityCompat.END);
    }
    private void loadData(){
        GetProductosService service = new GetProductosService();
        service.getProductos(response -> {
            if(response == null){
                // Mostrar mensaje de error
                Log.e("HomeActivity", "Response is null");
            }else{
                // Cargar los productos en la vista

                productos = ((ProductosResponse) response).getProductos();
                loadCategories();
            }
        }, ex -> {
            // Mostrar mensaje de error
            Log.e("HomeActivity", "Error al cargar los productos", ex);
        });
    }
    private void loadCategories() {
        GetCategoriasService service = new GetCategoriasService();
        service.getCategorias(response -> {
            if(response == null) {
                // Mostrar mensaje de error
                Log.e("HomeActivity", "Response is null");

            }else {
                categorias = ((CategoriasResponse) response).getCategorias();
                // Cargar las categorías en la vista

                addRecyclerViews();
            }
        }, ex -> {
            // Mostrar mensaje de error
            Log.e("HomeActivity", "Error al cargar las categorías", ex);
        });
    }

    private void addRecyclerViews() {
        // Añadir los recyclerviews a la vista
        for (Categoria categoria : categorias) {
            // Crear un recyclerview y un textview por cada categoría
            TextView textView = new TextView(this);
            textView.setText(categoria.getNombre());
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setTextSize(20);
            homeLayout.addView(textView);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);

            RecyclerView recyclerView = new RecyclerView(this);
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            List<Producto> productosCategoria = productos.stream()
                    .filter(producto -> producto.getCategoria().getId().equals(categoria.getId()))
                    .collect(Collectors.toList());
            HomeListAdapter adapter = new HomeListAdapter(productosCategoria, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            homeLayout.addView(recyclerView);
            adapter.notifyDataSetChanged();


        }
    }


}